<template>
  <div v-if="board.열렸니" class="comment-input-div">
    <div>
      <ul>
        <li class="comment-li" v-for="(item, index) in board.commentList" :key="index">
          <div v-if="item.answerDelAt === 'N'" class="content-wrapper">
          <div>
            <div class="info-wrapper">
              <span class="name">{{item.member.memNick}}</span>
              <span class="date">{{item.answerDate.substring(0, 19).replace("T", " ")}}</span>
            </div>
            <textarea class="text-area" v-model="item.answerCn" :readonly="item.isModify"></textarea>
          </div>
          <div>
            <i @click="this.changeCommentsIsOpen(item)" v-if="item.isOpen === false && board.codeDetail.codeDetailIdx === '7'" class="fas fa-ellipsis-h"></i>
            <!-- 여기서 토큰 값을 비교해서 본인의 게시글일 경우 수정,삭제 아이콘을 보여주고
                  본인의 글이 아니면 신고 버튼을 보여준다. v-if를 통해서
                  <div>
                    <i>신고</i>
                  </div>
            -->
            <div class="box" v-if="item.isOpen === true && item.isFinish === false">
              <i @click="this.changeIsModify(item); this.changeIsFinish(item)" class="fas fa-edit"></i>

              <i @click="confirmDeleteComment(item, board)" class="far fa-trash-alt"></i>
              <i @click="this.changeCommentsIsOpen(item)" class="fas fa-long-arrow-alt-left"></i>
            </div>

            <div id="finish-id" v-if="item.isOpen === true && item.isFinish === true"
                  @click="toUpdate(item); this.changeIsFinish(item)">Finish</div>
          </div>
          </div>
        </li>  
      </ul>
      <div id="btn-div">
        <button v-if="board.totalComments - board.commentList.length !== 0
                      && board.totalComments - board.commentList.length > 0"
                id="more-btn"
                @click="this.extraComments(board)">더 보기</button>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapMutations } from 'vuex'

export default {
  name: 'BoardComment',
  data() {
    return {
    }
  },

  props: {
    board: Object
  },

  computed : {
        ...mapActions({
          getComments : 'community/getComments',
        }),
  },

  methods: {
    ...mapActions({
      extraComments: 'community/extraComments'
    }),

    ...mapMutations({
      changeCommentsIsOpen : 'community/changeCommentsIsOpen',
      changeCommentsIsUpdate : 'community/changeCommentsIsUpdate',
      changeIsFinish : 'community/changeIsFinish',
      changeIsModify : 'community/changeIsModify'
    }),

    confirmDeleteComment(item, board){
      if(confirm("해당 댓글을 삭제하시겠습니까?")){
        this.deleteComment(item, board)
      }
    },

    //댓글 삭제
    deleteComment(item, board){
      console.log('------------')
      console.log(board)
      console.log(item)
      console.log('------------')
      this.axios.post('/deleteComment',{
                          answerIdx : item.answerIdx,
                          token : sessionStorage.getItem("token")
                      })
                .then(e => {
                    item.answerDelAt = e.data.answerDelAt
                    board.commentsOnView -= 1
                    board.totalComments = e.data.board.totalComments
                  })
    },

    toUpdate(item){
      this.axios.post('/updateComment', {
            token : sessionStorage.getItem("token"),
            answerIdx : item.answerIdx,
            content : item.answerCn
        }).then(e => {
          console.log(e)
          this.changeIsModify(item)
        })

    },

  },
}
</script>

<style scoped>
.comment-input-div {
  padding-right: 20px;
  padding-left: 20px;
}

.name-span {
  margin-right: 10px;
}

.comment-li {
  margin-top: 20px;
  display: flex;
  width: 100%;
  align-items: center;
}

.content-wrapper {
  background-color: #414556;
  padding: 10px;
  font-size: 14px;
  border-radius: 5px;
  width: 95%;
  height: fit-content;
  display: flex;
  justify-content: space-between;
}

.info-wrapper {
  margin-bottom: 5px;
}

.info-wrapper .date {
  font-size: 10px;
  margin-left: 10px;
}

.content-detail {
  margin-left: 10px;
}

.box {
  display: flex;
  flex-direction: column;
  font-size: 12px;
  gap:8px;
  height: 20px;
}

i {
  cursor: pointer;
}

.comment-div {
    display: flex;
    justify-content: right;
    padding-right: 20px;
    padding-bottom: 20px;
}

#comment-btn {
  color: black;
}

textarea {
  background-color: #414556;
  color: #fff;
  height: auto;
  resize: none;
  outline: none;
  border: none;
  margin : 0;
  padding: 0;
  overflow: hidden;
}

#finish-id {
  cursor: pointer;
}

#btn-div {
  padding-top: 10px;
  display: flex;
  justify-content: center;
}

#btn-div > button {
  color: white;
  background-color: coral;
  border-radius: 10px;
  padding: 5px 10px;
}
</style>