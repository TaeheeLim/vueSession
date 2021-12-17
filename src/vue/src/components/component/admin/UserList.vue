<template>
  <div>
    <div class="header">
      <span class="title">User List</span>
      <input class="search" type="text" placeholder="🔎" />
    </div>
    <div class="body-container">
      <ul class="body">
        <li
          v-for="(data, index) in userListData"
          :key="index"
          :start="data.start"
          :end="data.end"
        >
        <span>  Id : {{ data.id }} #{{ data.tag }}  </span>
        
        <span style="text-align:left"> 상태 : 
          <!-- {{ data.start !== '' ? isBan(data.start, data.end) : '정상' }} -->
            <select>
          <option :selected="data.start == ''" value=""> 정상 </option>
          <option :selected="isBan(data.start, data.end)=='7일 정지'" value="7일 정지"> 7일 정지 </option>
          <option :selected="isBan(data.start, data.end)=='30일 정지'" value="30일 정지"> 30일 정지 </option>
          <option :selected="isBan(data.start, data.end)=='영구 정지'" value="영구 정지"> 영구 정지 </option>
        </select>
        </span>
        <span> {{ data.start !== '' ? getState(data.end) : null }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import moment from "moment";
export default {
  data() {
    return {
      state: "",
      start: "",
      end: "",
    };
  },
  computed: {
    ...mapState({
      userListData: (state) => state.userList.userListData,
    }),
  },
  methods: {
    isBan(start, end) {

      let startDay = moment(start,"YYYY-MM-DD")
      let endDay = moment(end,"YYYY-MM-DD")

      let info = startDay.from(endDay).split(" ");
      // 7일정지 , 30일정지 , 영구정지

      switch(info[0]){
        case 'a':
        case 'an' : return '30일 정지'
        case '7' : return '7일 정지'
        default : return '영구 정지'
      }



    },
    getState(end) {

      let date = moment(end, "YYYY-MM-DD").fromNow(true).split(" ");

      if (date[0] === "a" || date[0] === "an") {
        return `(1일 남음)`;
      } else {
        return `(${date[0]}일 남음)`;
      }
    },
  },
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 40px;
}

.title {
  font-size: 28px;
  color: #fff;
}

.search {
  background: #414556;
  width: 150px;
  border: none;
  outline: none;
  border-radius: 6px;
  color: #fff;
}

.body-container {
  height: 80%;
  overflow: scroll;
}

.body {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  overflow: scroll;
  row-gap: 22px;
}

.body li {
  display: grid;
  grid-template-columns: 180px 118px 100px;
  align-content: center;
  background: #2c2f3b;
  height: 30px;
  width: 90%;
  color: #fff;
  border-radius: 6px;
  margin: auto;
  text-align: center;
}

.body li span select {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  width: fit-content;
  background: none;
  color: #fff;
  border: none;
  outline: none;
}

.body li span select option {
  background: #2c2f3b;
  color: #fff;
}

::-webkit-scrollbar {
  width: 0px;
}
</style>