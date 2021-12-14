<template>
   <h1>Filter</h1>
    <ul>
      <li> 
        <input checked type="checkbox" class="filter-wrap" id="all" @click="selectedCheckbox">
        <label for="all">전체</label>
      </li>
      <li> 
        <input type="checkbox" class="filter-wrap" id="common" @click="selectedCheckbox">
        <label for="common">공통</label>
      </li>
      <li> 
        <input type="checkbox" class="filter-wrap" id="individual" @click="selectedCheckbox">
        <label for="individual">개인</label>
      </li>
      <li> 
        <input type="checkbox" class="filter-wrap" id="notice" @click="selectedCheckbox">
        <label for="notice">공지</label>
      </li>
      <li> 
        <input type="checkbox" class="filter-wrap" id="Emergency" @click="selectedCheckbox">
        <label for="Emergency">긴급</label>
      </li>
      <li> 
        <input type="checkbox" class="filter-wrap" id="vacation" @click="selectedCheckbox">
        <label for="vacation">휴가</label>
      </li>
      <li> 
        <input type="checkbox" class="filter-wrap" id="note" @click="selectedCheckbox">
        <label for="note">기타</label>
      </li>
    </ul>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  data() {
    return {
      filterFlag : false,
    }
  },
  methods : {
    ...mapMutations({
      setFilterValue : 'scheduler/setFilterValue',
    }),
    selectedCheckbox(e){
      if(e.type === 'click'){
        const chkBox = document.querySelectorAll('.filter-wrap')
        for(let i = 0; i < chkBox.length; i++){
          if(!this.filterFlag){
            if(e.target.id !== chkBox[i].id){
              chkBox[i].checked = false
            }
            this.filterFlag = !this.filterFlag
            //this.filterFlag => true -> unchecked
          }else if(this.filterFlag){
            if(chkBox[i].id === 'all'){
              chkBox[i].checked = true
            }
            this.filterFlag = !this.filterFlag
          }
        }
      }
      this.setFilterValue(e.target.id)
    },
  },
}
</script>

<style>

</style>