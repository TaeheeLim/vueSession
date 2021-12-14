<template>
  <div class="detail-container">
    <div class="detail-header">
      <span v-if="!isUpdate" class="detail-title">일감 상세</span>
      <span v-else-if="isUpdate" class="detail-title">일감 수정</span>
      <span class="button-zone" v-if="detail != '' && !isUpdate">
        <button @click="isUpdate = true" class="update-btn btn">수정</button>
        <button @click="deleteLine" class="delete-btn btn">삭제</button>
      </span>
      <span class="button-zone" v-if="isUpdate">
        <button @click="completeModify" class="update-btn btn">완료</button>
        <button @click="cancelModify" class="delete-btn btn">취소</button>
      </span>
    </div>
    <hr class="detail-line" />

    <!-- 선택 안했을때 (detail == null) 보여질 Element 시작 -->
    <table v-if="detail == ''" class="detail-table">
      <tr>
        <td>상단에서 일감을 선택해주세요</td>
      </tr>
    </table>
    <!-- 선택 안했을때 (detail == null) 보여질 Element 끝 -->

    <!-- 선택 했을때 (detail != null) 보여질 Element 시작 -->
    <table v-else-if="detail != '' && !isUpdate" class="detail-table">
      <tr>
        <th>상태</th>
        <td>{{ detail.state }}</td>
        <th>진척도</th>
        <td class="detail-progress">
          <div class="detail-item-progress">
            <div
              :style="{
                background: detail.priority,
                width: `${detail.progress}%`,
              }"
              class="detail-item-progress-fill"
            ></div>
          </div>
          <span class="detail-item-progress-percent"
            >{{ detail.progress }}%</span
          >
        </td>
      </tr>
      <tr>
        <th>우선순위</th>
        <td>{{ detail.priority }}</td>
        <th>시작일</th>
        <td>{{ detail.start }}</td>
      </tr>
      <tr>
        <th>담장자</th>
        <td>{{ detail.memId }}</td>
        <th>마감임</th>
        <td>{{ detail.end }}</td>
      </tr>
      <tr>
        <th>설명</th>
      </tr>
      <tr>
        <td colspan="4">{{ detail.content }}</td>
      </tr>
    </table>
    <!-- 선택 했을때 (detail != null) 보여질 Element 끝 -->

    <!-- 수정 모드 일때 (isUpdate == true) 보여질 Element 시작 -->
    <table v-else-if="isUpdate" class="detail-table">
      <tr>
        <th>상태</th>
        <td><input type="text" :value="`${detail.state}`"/></td>
        <th>진척도</th>
        <td class="detail-progress">
          <div class="detail-item-progress">
            <div
              :style="{
                background: detail.priority,
                width: `${detail.progress}%`,
              }"
              class="detail-item-progress-fill"
            ></div>
          </div>
          <span class="detail-item-progress-percent">
            <input type="text" :value="`${detail.progress}`" /></span>
        </td>
      </tr>
      <tr>
        <th>우선순위</th>
        <td><input type="text" :value="`${detail.priority}`" /></td>
        <th>시작일</th>
        <td><input type="text" :value="`${detail.start}`"/></td>
      </tr>
      <tr>
        <th>담장자</th>
        <td><input type="text" :value="`${detail.memId}`"/></td>
        <th>마감임</th>
        <td><input type="text" :value="`${detail.end}`"/></td>
      </tr>
      <tr>
        <th>설명</th>
      </tr>
      <tr>
        <td colspan="4"><input type="text" :value="`${detail.content}`"/></td>
      </tr>
    </table>
    <!-- 수정 모드 일때 (isUpdate == true) 보여질 Element 끝 -->
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

export default {
  data() {
    return {
      isUpdate: false,
      inputState: "",
      inputProgress: "",
      inputPriority: "",
      inputStart: "",
      inputEnd: "",
      
    };
  },
  computed: mapState({
    detail: (state) => state.gantt.detail,
  }),
  methods: {
    ...mapMutations({
      update: "gantt/update",
      delete: "gantt/delete",
    }),
    deleteLine() {
      this.delete();
    },
    completeModify() {
      this.isUpdate = true;
      let payload = "";
      this.update(payload);
    },
    cancelModify() {
      this.isUpdate = false;
    },
  },
};
</script>

<style scoped>
.detail-container {
  border-radius: 25px;
  margin: 20px;
  height: calc(30vh - 40px);
  width: 100%;
  padding: 20px;
  background: #2c2f3b;
}

.detail-header {
  display: flex;
  justify-content: space-between;
  width: 50%;
}

.detail-title {
  font-size: 22px;
}
.detail-line {
  width: 50%;
  margin: 0 0 6px 0;
}

.update-btn {
  background: #ff8906;
}

.delete-btn {
  background: #ff5c5c;
}

.btn {
  color: white;
  margin: 0 0 10px 10px;
  border-radius: 15px;
  width: 50px;
  padding: 2px;
  -webkit-filter: drop-shadow(0px 15px 15px rgba(10, 10, 10, 0.8));
}

.detail-table {
  border: none;
  width: 90%;
  text-align: left;
  height: 80%;
  transition: all 0.6s linear 0.2s;
  animation: show;
}

.detail-table th,td {
  width: 30px;
}

.detail-table input{
  border: none;
  outline: none;
  background: none;
  color: #ff8906;
  width: 30px;
}

.detail-progress {
  position: relative;
}

.detail-item-progress {
  height: 20px;
  max-width: 60%;
  background: #414556;
}

.detail-item-progress-fill {
  height: 20px;
  width: 50%;
  background: #3f80a9;
}

.detail-item-progress-percent {
  position: absolute;
  top: 0;
  right: 10%;
}

@keyframes show {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
</style>