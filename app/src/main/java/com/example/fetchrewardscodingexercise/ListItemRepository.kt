package com.example.fetchrewardscodingexercise

class ListItemRepository {

    private val apiService = RetrofitInstance.apiService

    suspend fun getListItems(): Map<Int, List<ListItem>> {
        // Fetch items from the API
        val items = apiService.fetchListItems()

        // Filter out any items where "name" is blank or null
        val filteredItems = items.filter { !it.name.isNullOrBlank() }

        // Sort the items by listId then by the name (smaller number to larger)
        val sortedItems = filteredItems.sortedWith(
            compareBy<ListItem> {it.listId}
                .thenBy { extractNumber(it.name) }
        )

        // Sort the results first by "listId" then by "name" when displaying
        val groupedItems = sortedItems.groupBy { it.listId }

        return groupedItems
    }

    private fun extractNumber(name: String): Int {
        // Extracts the number from the name of the item for sorting
        val regex = "\\d+".toRegex()
        val matchResult = regex.find(name)
        if (matchResult != null) {
            return matchResult.value.toInt()
        } else {
            return Int.MAX_VALUE
        }
    }
}