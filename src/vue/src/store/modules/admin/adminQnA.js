const adminQnA = {
  namespaced: true,
  state: {
    qnaData:[
      {
      qnaId: "1",
      memId : "zerochae",
      date: "2021-01-01 22:10",
      content : "오예 거의 다 함 ㅋㅋㅋ 1",
      replies: [
        {
          memId: "zerochae",
          content: "복구 완료 1",
          date: "2021-01-01 22:10",
        },
        {
          memId: "admin",
          content : "ㄴㄴ 복구중임 1",
          date: "2021-01-01 22:10",
        },
      ]
    },
      {
      qnaId: "2",
      memId : "taehee",
      date: "2021-01-02 22:10",
      content : "오예 거의 다 함 ㅋㅋㅋ 2",
      replies: [
        {
          memId: "zerochae",
          content: "복구 완료 2",
          date: "2021-01-01 22:10",
        },
        {
          memId: "admin",
          content : "ㄴㄴ 복구중임 2",
          date: "2021-01-01 22:10",
        },
      ]
    },
      {
      qnaId: "3",
      memId : "kade",
      date: "2021-01-03 22:10",
      content : "오예 거의 다 함 ㅋㅋㅋ 3",
      replies: [
        {
          memId: "zerochae",
          content: "복구 완료 3",
          date: "2021-01-01 22:10",
        },
        {
          memId: "admin",
          content : "ㄴㄴ 복구중임 3",
          date: "2021-01-01 22:10",
        },
      ]
    },
      {
      qnaId: "4",
      memId : "juwon",
      date: "2021-01-04 22:10",
      content : "오예 거의 다 함 ㅋㅋㅋ 4",
      replies: [
        {
          memId: "zerochae",
          content: "복구 완료 4",
          date: "2021-01-01 22:10",
        },
        {
          memId: "admin",
          content : "ㄴㄴ 복구중임 4",
          date: "2021-01-01 22:10",
        },
      ]
    },

  ]
    
  },
  mutations: {
    insert(state,payload){

      let index = payload[0]; 
      let data =  payload[1];
      
      state.qnaData[index].replies.push(data)
    },
  },
  actions: {
    
  }
}

export default adminQnA