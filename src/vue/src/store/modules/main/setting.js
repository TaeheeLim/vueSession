const setting = {
  namespaced: true,
  state() {
    return {
      clickState  : false,
      roleList : [// 여기에 ajax로 요청한 데이터 들어와야함.
        'PM','PL','DA','TA','AA','UA','BA','EA','SA'
      ],
      projectDate : {
          startDate : '2021/12/08',
          endDate : '2021/12/25'
      },
      userInputDate : {
          startDate : '',
          endDate : ''
      },
      roleMatch : [],
      searchMemberList : [],
      selectMemberList : [],
      projectMemberList : []
    }
  },
  mutations: {
    changeClickState(state){
      if(state.clickState == false){
        state.clickState = true;
      } else{
        state.clickState = false;
      }
    },
  },
  actions: {
    
  }
}

export default setting