<template>
  <div>
    <div class="header">
      <span class="title">QnA</span>
      <input class="search" type="text" placeholder="🔎" />
    </div>

    <div class="body">
      <div class="card" v-for="(data, index) in qnaData" :key="index">
        <p class="card-header"> <span class="board-writer">{{ data.memId }}</span>  <span class="board-date">{{ getDate(data.date) }}</span></p>
        <p class="card-body"> {{ data.content }} </p>
        <input
          type="text"
          @keyup.enter="addReply(index)"
          @input="setText"
          placeholder="댓글을 입력하세요"
        />
        <p class="card-reply" v-for="reply in data.replies" :key="reply">
          <span class="reply-writer"> {{reply.memId}}  <span class="reply-date">{{getDate(reply.date)}}</span> </span> 
          <span class="reply-content"> {{ reply.content }} </span>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";
import moment from "moment";
export default {
  data() {
    return {
      inputText: "",
    };
  },
  computed: {
    ...mapState({
      qnaData: (state) => state.adminQnA.qnaData,
    }),
  },
  methods: {
    ...mapMutations({
      insert: "adminQnA/insert",
    }),
    setText(e){
      this.inputText = e.target.value 
    },
    getDate(date) {
      let temp = moment(date, "YYYY-MM-DD HH:mm:ss");
      return `${temp.format("M")}월 ${temp.format("D")}일 ${temp.format("HH")}:${temp.format("mm")}`;
    },
    addReply(index) {
      let payload = [];
      let data = {
        memId: "admin",
        content: this.inputText,
      };
      payload.push(index,data);
      this.insert(payload);

      this.inputText = ""
      document.querySelector('.body input').value = ""
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

.body {
  width: 90%;
  margin: auto;
  height: 94%;
  overflow: scroll;
}

.board-writer{
  font-size: 22px;
}

.board-date{
  font-size: 12px;
}

.card {
  width: 100%;
  height: fit-content;
  background: #2c2f3b;
  margin-bottom: 20px;
  padding: 20px;
  color: #fff;
  border-radius: 8px;
}

.card-body{
  font-size: 22px;
}

.card > * {
  margin-bottom: 15px;
  padding: 6px;
  border-radius: 8px;
}

.card-header {
  font-size: 20px;
}

.card input {
  background: #414556;
  border: none;
  outline: none;
  height: 30px;
  padding: 10px;
  width: 100%;
  color: #fff;
}

.card-reply {
  background: #414556;
  width: fit-content;
  display: flex;
  flex-direction: column;
  padding: 14px;
}

.reply-writer{
  font-size: 18px;
}

.reply-date{
  font-size: 12px;
}

.reply-content{
  font-size: 18px;
  margin-top: 12px;
}

::-webkit-scrollbar {
  width: 0px;
}
</style>