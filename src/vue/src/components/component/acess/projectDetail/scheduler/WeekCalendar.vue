<template>
  <button style="color : white;" @click="this.toggleAllDayContent">AllDay 커스텀</button>
  <div>
    <vue-cal
        small
        locale='ko'
        hide-view-selector
        :time-from="0 * 60"
        :time-to="24 * 60"
        active-view="week"
        :disable-views="['years', 'year', 'month', 'day']"
        resize-x
        :editable-events="{ title: true, drag: true, resize: true, delete: true, create: false }"
        :events=$store.state.scheduler.data
        class="vuecal--dark-theme vuecal--full-height-delete"

        @event-delete="deleteEventFunction"
        @event-duration-change="resizeUpdateEventFunction"
        @event-drop="dropUpdateEventFunction"

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
import { mapMutations } from 'vuex'
import VueCal from 'vue-cal'

export default {
  components:{
    VueCal,
  },
  data() {
    return {
      eventsCssClasses: ['common', 'individual', 'notice', 'Emergency', 'vacation', 'note', 'gantt'],
      showEventCreationDialog: false,
      changeTheme : false,
      changeLang : false,
    }
  },
  mounted() {
  },
  methods: {
    ...mapMutations({
      // 단일 vuex일때는 'store명/함수명'이 아닌 '함수명'을 기재해야 작동한다.
      setModal : 'scheduler/setModal',
      setCallAddFunction : 'scheduler/setCallAddFunction',
      closeModal : 'scheduler/closeModal',
      toggleAllDayContent : 'scheduler/toggleAllDayContent',
    }),

    deleteEventFunction(e){
      let copy = [...this.$store.state.scheduler.data];
      for(let i = 0; i<copy.length; i++){
        if(e.id === copy[i].id){
          copy.splice(i,1);
        }
      }
      this.$store.state.scheduler.data = copy;
    },

    dropUpdateEventFunction(e){
      let copy = [...this.$store.state.scheduler.data];
      let momentedStartTime = e.event.start.format('YYYY-MM-DD HH:mm')
      let momentedEndTime = e.event.end.format('YYYY-MM-DD HH:mm')

      for(let i = 0; i<copy.length; i++){
        if(e.event.id === copy[i].id){
          copy[i].start = momentedStartTime
          copy[i].end = momentedEndTime
        }
      }
      this.$store.state.scheduler.data = copy;
    },
    resizeUpdateEventFunction(e){
      let momentedEndTime=e.event.end.format('YYYY-MM-DD HH:mm')
      let copy = [...this.$store.state.scheduler.data];

      for(let i = 0; i < copy.length; i++){
        if(e.event.id === copy[i].id){
          copy[i].end = momentedEndTime
        }
      }
      this.$store.state.scheduler.data = copy;
    },
  },
}
</script>

<style>

</style>