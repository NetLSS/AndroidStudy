# data binding

```kotlin
    private fun observeViewModel() {
        binding?.run {
            item.groupItemLiveData.observe(
                owner,
                EventObserver(this@BestItemViewHolder) { groupItemData, _ ->
                    groupItemData ?: return@EventObserver
                    try {
                        groupItemCount = groupItemData.groupItemCount
                        isVisibleGroupItemCount = groupItemData.groupItemCount > 0
                    } catch (e: Exception) {
                        EbayLog.d("observeViewModel", "${e.message}")
                    }

                })
        }
    }
```


```kotlin
val groupItemLiveData: MutableLiveData<Event<GroupItemData?>> = MutableLiveData(null) // 잘못된 초기화

val groupItemLiveData: MutableLiveData<Event<GroupItemData?>> = MutableLiveData(Event(null)) // 이렇게...


```