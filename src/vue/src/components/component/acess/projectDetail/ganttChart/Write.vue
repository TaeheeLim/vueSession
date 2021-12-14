<template>
  <div class="write-container">
    <div class="write-header">
      <span class="write-title">일감 등록</span>
      <span
        ><button @click="addTask()" class="write-btn btn">작성</button></span
      >
    </div>
    <hr class="write-line" />
    <table class="write-table">
      <tr>
        <th>상태</th>
        <td><input type="text" /></td>
        <th>진척도</th>
        <td>
          <div class="write-item-progress">
            <div class="write-item-progress-fill"></div>
          </div>
          50%
        </td>
      </tr>
      <tr>
        <th>우선순위</th>
        <td><input type="text" /></td>
        <th>시작일</th>
        <td><input type="text" /></td>
      </tr>
      <tr>
        <th>담장자</th>
        <td>kade</td>
        <th>시작일</th>
        <td><input type="text" /></td>
      </tr>
      <tr>
        <th>설명</th>
      </tr>
      <tr>
        <td colspan="4"><input type="text" /></td>
      </tr>
    </table>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
import moment from "moment";

export default {
  data() {
    return {};
  },
  methods: {
    ...mapMutations({
      insert: "gantt/insert",
    }),
    addTask() {
      let today = moment().format("YYYY-MM-DD").split("-");

      let payload = [];

      payload.push(today[1]);
      payload.push({
        memId: "daeg",
        title: "task3",
        content: "내용3",
        start: "11",
        end: "14",
        state: "Y",
        priority: "cornflowerblue",
        progress: "50",
        img: "con1.jpg",
      });

      this.insert(payload);
    },
  },
};
</script>

<style scoped>
.write-container {
  border-radius: 25px;
  margin: 20px;
  height: calc(30vh - 40px);
  width: 100%;
  padding: 20px;
  background: #2c2f3b;
}

.write-header {
  display: flex;
  justify-content: space-between;
  width: 50%;
}

.write-title {
  font-size: 22px;
}

.write-line {
  width: 50%;
  margin: 0 0 6px 0;
}

.write-btn {
  background: #ff8906;
}

.btn {
  color: white;
  margin: 0 0 10px 10px;
  border-radius: 15px;
  width: 50px;
  padding: 2px;
  -webkit-filter: drop-shadow(0px 15px 15px rgba(10, 10, 10, 0.8));
}

.write-table {
  border: none;
  width: 90%;
  text-align: left;
  height: 80%;
}

.write-table input {
  width: 110px;
  background: none;
  border: none;
  outline: none;
  color: #fff;
}

.write-table th,
td {
  width: 30px;
}

.write-des-text {
}

.write-item-progress {
  /* position: relative; */
  right: 0;
  display: inline-block;
  height: 20px;
  max-width: 100%;
  background: #fff;
}

.write-item-progress-fill {
  /* display: inline-block; */
  height: 20px;
  width: 50%;
  background: #3f80a9;
}
</style>