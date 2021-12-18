<template>
  <button style="color : white;" @click="this.toggleAllDayContent">AllDay 커스텀</button>
  <div>
    <vue-cal
        small
        locale='ko'
        hide-view-selector
        :time-from="0 * 60"
        :time-to="24 * 60"
        :transitions="false"
        active-view="week"
        :disable-views="['years', 'year', 'month', 'day']"
        resize-x
        :editable-events="{ title: true, drag: true, resize: true, delete: true, create: false }"
        :events=$store.state.scheduler.data
        class="vuecal--dark-theme vuecal--full-height-delete"

        @event-delete="deleteEventFunction"
        @event-duration-change="updateEventFunction"
        @event-drop="updateEventFunction"

        :selected-date=$store.state.scheduler.selectedDate
        ref="vuecal"
        :drag-to-create-threshold="20"
        style="width: 100% ;height: 100%;"
        :show-all-day-events="['short', true, false][this.$store.state.scheduler.showAllDayEvents]"
    >
    </vue-cal>
  </div>

</template>

<script scoped>
import { mapMutations, mapState } from 'vuex'
import VueCal from 'vue-cal'

export default {
  components:{
    VueCal,
  },
  data() {
    return {
      eventsCssClasses: ['common', 'individual', 'notice', 'emergency', 'vacation', 'note', 'gantt'],
      showEventCreationDialog: false,
      changeTheme : false,
      changeLang : false,
    }
  },
  computed : {
    ...mapState({
      datas : state => state.scheduler.data,
    })
  },
  mounted() {
    this.callCalendarData()
  },
  methods: {
    ...mapMutations({
      // 단일 vuex일때는 'store명/함수명'이 아닌 '함수명'을 기재해야 작동한다.
      setModal : 'scheduler/setModal',
      setCallAddFunction : 'scheduler/setCallAddFunction',
      closeModal : 'scheduler/closeModal',
      toggleAllDayContent : 'scheduler/toggleAllDayContent',
      setData : 'scheduler/setData',
    }),
    deleteEventFunction(e){
      let copy = [...this.$store.state.scheduler.data];
      for(let i = 0; i<copy.length; i++){
        if(e.id === copy[i].id){
          copy.splice(i,1);
        }
      }
      this.setData(copy)
      this.callAxios('delete', e)
    },

    updateEventFunction(e){
      let momentedStartTime = e.event.start.format('YYYY-MM-DD HH:mm')
      let momentedEndTime = e.event.end.format('YYYY-MM-DD HH:mm')
      let dataArr = {
        calIdx : e.event.id,
        calStartDate : momentedStartTime,
        calEndDate : momentedEndTime,
        calColor : e.event.class,
        calCn : e.event.content,
        calTitle : e.event.title,
        class : e.event.class,
      }

      for(let i = 0; i < this.datas.length; i++){
        if(this.datas[i].id === e.event.id){
          this.datas[i].start = momentedStartTime
          this.datas[i].end = momentedEndTime
          this.datas[i].title = e.event.title
          this.datas[i].content = e.event.content
          this.datas[i].class = e.event.class
        }
      }
      this.callAxios('update', dataArr)
    },

    callCalendarData(){
      let copy = [...this.$store.state.scheduler.data];

      const url = '/calendar/getAllSchedules'
      this.axios.get( url, {
        params : {
          'project.prjctIdx' : 1,
          'member.memIdx' : 1,
        }
      })
          .then( (r)=>{
            for(let i = 0; i < r.data.length; i++){
              r.data[i].calStartDate = r.data[i].calStartDate.replace('T', ' ')
              r.data[i].calEndDate = r.data[i].calEndDate.replace('T', ' ')
              const arr = {
                id :  r.data[i].calIdx,
                start: r.data[i].calStartDate,
                end: r.data[i].calEndDate,
                title: r.data[i].calTitle,
                content: r.data[i].calCn,
                class: r.data[i].calColor,

                // 옵션들을 다룰수있지만 처리하지않음
                // deletable: true,
                // resizable: true,
                // draggable: true,
                // allDay : false,
                // isgantt : false,
              }
              copy.push(arr)

              // 삭제여부, 삭제이유도 넘어오지만 처리하지않음
              // r.data[i].calDelAt
              // r.data[i].calDelResn
            }
            if(this.datas.length === 0){
              this.setData(copy)
            }

          })
    },

    callAxios(type, dataArr){
      switch (dataArr.class) {
        case "common":
          dataArr.class = 1
          break;
        case "individual":
          dataArr.class = 2
          break;
        case "notice":
          dataArr.class = 3
          break;
        case "emergency":
          dataArr.class = 4
          break;
        case "vacation":
          dataArr.class = 5
          break;
        case "note":
          dataArr.class = 6
          break;
      }
      if(type === 'update'){
        const url = '/calendar/updateSchedule'
        this.axios.post( url, null, {
          params : {
            calIdx : dataArr.calIdx,
            calStartDate : dataArr.calStartDate ,
            calEndDate : dataArr.calEndDate ,
            calColor : dataArr.calColor,
            calTitle : dataArr.calTitle,
            calCn : dataArr.calCn,
            'project.prjctIdx' : 1,
            'member.memIdx' : 1,
            'codeDetail.codeDetailIdx' : dataArr.class,
            'codeDetail.masterCode.masterCodeIdx' : 'CAL',
          }
        })
      }

      if(type === 'delete'){
        const url = '/calendar/deleteSchedule'
        this.axios.post( url, null, {
          params : {
            calIdx : dataArr.id,
          }
        })

      }

    }

  },
  watch : {
  }
}
</script>

<style>

.vuecal--dark-theme.vuecal--full-height-delete{
  transition: all 1s ease;
}

</style>