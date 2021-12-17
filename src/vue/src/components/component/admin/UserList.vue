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
          <span> Id : {{ data.id }} #{{ data.tag }} </span>

          <span style="text-align: left">
            상태 :
            <!-- {{ data.start !== '' ? isBan(data.start, data.end) : '정상' }} -->
            <select @change="(e) => modifyState(e, data.id)">
              <option :selected="data.start == ''" value="정상">정상</option>
              <option
                :selected="isBan(data.start, data.end) == '7일 정지'"
                value="7일 정지"
              >
                7일 정지
              </option>
              <option
                :selected="isBan(data.start, data.end) == '30일 정지'"
                value="30일 정지"
              >
                30일 정지
              </option>
              <option
                :selected="isBan(data.start, data.end) == '영구 정지'"
                value="영구 정지"
              >
                영구 정지
              </option>
            </select>
          </span>
          <span v-if="isBan(data.start, data.end) != '영구 정지'">
            {{ data.start !== "" ? getState(data.end) : "" }}
          </span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";
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
    ...mapMutations({
      update: "userList/update",
    }),
    isBan(start, end) {
      let startDay = moment(start, "YYYY-MM-DD");
      let endDay = moment(end, "YYYY-MM-DD");

      let info = startDay.from(endDay).split(" ");
      // 7일정지 , 30일정지 , 영구정지
      switch (info[0]) {
        case "a":
        case "an":
          return "30일 정지";
        case "7":
          return "7일 정지";
        default:
          return "영구 정지";
      }
    },
    getState(end) {
      let date = moment(end, "YYYY-MM-DD").fromNow(true).split(" ");
      let result = "";
      result += date[0] === "a" || date[0] === "an" ? 1 : date[0];
      result +=
        date[1] === "day" || date[1] === "days"
          ? "일"
          : date[1] === "month" || date[1] === "months"
          ? "달"
          : "년";
      result += " 남음";
      return result;
    },
    modifyState(e, id) {
      let payload = [];
      payload.push(id, e.target.value);
      this.update(payload);
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
  grid-template-columns: 1fr 1fr 1fr;
  align-content: center;
  background: #2c2f3b;
  width: 90%;
  color: #fff;
  border-radius: 6px;
  margin: auto;
  text-align: center;
}

.body li span select {
  border: none;
  background: none;
  color: #fff;
  width: fit-content;
  outline: none;
  text-align: center;
}

.body li span select option {
  background: #2c2f3b;
  color: #fff;
}

::-webkit-scrollbar {
  width: 0px;
}

@media (max-width: 1770px) {


  .body li {
    display: flex;
    flex-direction: column;
    text-align: center;
    align-items: center;
    height: 60px;
    justify-content: center;
  }
}

@media (max-width:1268px){

  .body{
    height: 50vh;
    overflow: scroll;
  }

  .body li{
    height: 50px;
  }
}

@media (max-width:1100px) {
  .body{
    display: grid;
    grid-template-columns: 1fr 1fr;
    height: 50vh;
    overflow: scroll;
  }

  .body li{
    display: flex;
    flex-direction: column;
    height: 100px;
    padding: 10px;
  }

  .body li span:not(:last-child) {
    margin: 0 0 10px 0;
  }
}

@media(max-width: 535px){
  .header{
    display: flex;
    flex-direction: column;
  }

  .header .search{
    margin-top: 25px;
    height: 30px; 
    width: 100%;
  }

  .body li > *{
    font-size: 12px;
  }

  .body li span select{
    font-size: 12px;
  }

}

</style>