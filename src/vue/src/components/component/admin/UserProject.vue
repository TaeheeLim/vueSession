<template>
  <div>
    <div class="header">
      <span class="title">User Project</span>
      <input class="search" type="text" placeholder="🔎" />
    </div>

    <ul class="body">
      <li v-for="data in projectData" :key="data">
        <span> {{ data.name }} &nbsp; # {{ data.id }} </span>
        <span> 팀장 : &nbsp; {{ data.memId }} </span>
        <span>
          상태 :
          <select>
            <option :selected="data.isCom!==''" value="진행">진행</option>
            <option :selected="data.isCom!==''" value="완료">완료</option>
            <option :selected="data.isDel!==''" value="삭제">삭제</option>
          </select>
        </span>
        <span> 기간 : &nbsp; {{ data.start }} ~ {{ data.end }} </span>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  computed: mapState({
    projectData: (state) => state.userProject.projectData,
  }),
  data() {
    return {};
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
  border-radius: 8px;
  color: #fff;
}

.body {
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: scroll;
  height: 90%;
}

.body li {
  margin-bottom: 22px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  background: #2c2f3b;
  min-height: 30px;
  width: 90%;
  color: #fff;
  border-radius: 6px;
}

.body li span select {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  width: 35px;
  background: none;
  color: #fff;
  border: none;
  outline: none;
}

.body li span select option {
  background: #2c2f3b;
  color: #fff;
}

.body li:last-child {
  margin-bottom: 35px;
}

::-webkit-scrollbar {
  width: 0px;
}
</style>