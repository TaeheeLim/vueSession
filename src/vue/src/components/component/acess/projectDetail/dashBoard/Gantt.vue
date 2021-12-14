<template>
  <div class="container">
    <h2>Gantt Chart</h2>
    <div class="chart-container">
        <div class="gantt-wrap">

          <ul class="gantt-head">
            <li class="date-head"></li>
            <li class="item-head"
                v-for="item in ganttData.item" 
                :key="item">
              {{item.itemTitle}}
            </li>
          </ul>

          <ul class="gantt-body">
            <li class="top-date">
              <div>
                <span class="month-item"
                      v-for="(item, index) in ganttData.monthCnt"
                      :key="index"
                      v-bind:style="{width: `${ganttData.dayCnt[index - 1] * 30}px`}">
                  {{item}}
                </span>
              </div>
            </li>
            <li class="bottom-date">
              <div>
                <div v-for="item in ganttData.dayCnt" 
                      :key="item">
                    <span class="day-item" 
                          v-for="day in item" 
                          :key="day">
                      {{day}}
                    </span>
                </div>
              </div>
            </li>
            <li class="item-box"
                v-for="item in ganttData.item" 
                :key="item">
              <div>
                <span class="gantt-item-space"
                      v-for="i in item.itemSpace" 
                      :key="i">1</span>
                <span class="gantt-item"
                      v-for="i in item.itemLength" 
                      :key="i"
                      :style="{backgroundColor: item.itemColor}"></span>
              </div>
            </li>
          </ul>

        </div>
      </div>
    </div>
</template>

<script>
import gData from '../../../../../assets/gData.json'

export default {
  name: "gantt",
  data() {
    return {
      ganttData: {}
    }
  },
  methods: {
    getGanttData(gData) {
      const serverData = gData.ganttData
      const _data = {
        projectStart: serverData.projectStart,
        projectEnd: serverData.projectEnd,
        today: serverData.today,
        monthCnt: {},
        dayCnt: [],
        item: []
      }

      const startYear = new Date(serverData.projectStart).getFullYear()
      const endYear = new Date(serverData.projectEnd).getFullYear()
      const startMonth = new Date(serverData.projectStart).getMonth() + 1
      const endMonth = new Date(serverData.projectEnd).getMonth() + 1
      let totalMonth = endMonth - startMonth + 1
      if(startYear < endYear) {
        totalMonth += 12
      }

      // i => 총 월 수
      // j => 기준 월 부터 한달씩 증가
      // k => 일년이 지나면 초기화
      for(let i = 0, j = startMonth, k = startMonth; i < totalMonth; i++, j++, k++) {

        if(k === 13) {
          k = 1
        }

        const currentDate = new Date(serverData.projectStart)
        const d = new Date(currentDate.setMonth(currentDate.getMonth() + i))
        let date

        if(k < 10) {
          date = `${d.getFullYear()}-0${k}`
        } else {
          date = `${d.getFullYear()}-${k}`
        }

        _data.monthCnt[j] = date
      }

      let year = startYear
      for(let i = 0, j = startMonth; i < totalMonth; i++, j++) {
        if(j === 13) {
          year++
        }
        _data.dayCnt.push(new Date(year, j, 0).getDate())
      }

      // item 배열에 추가
      for(let item of serverData.item) {
        const isd = new Date(item.itemStart)
        const ied = new Date(item.itemEnd)
        const psd = new Date(serverData.projectStart)

        const itemLen = Math.ceil((ied.getTime()-isd.getTime())/(1000*3600*24)) + 1
        const itemSpa = Math.ceil((isd.getTime()-psd.getTime())/(1000*3600*24))

        const ganttItem = {
          itemTitle: item.itemTitle,
          itemStart: item.itemStart,
          itemEnd: item.itemEnd,
          itemSpace: itemSpa,
          itemLength: itemLen,
          itemColor: item.itemColor
        }

        _data.item.push(ganttItem)
      }

      this.ganttData = _data
    }
  },
  mounted() {
    this.getGanttData(gData)
  }
}
</script>

<style scoped>
.container {
  width: 70vw;
  height: 510px;
  background: #2C2F3B;
  padding: 10px;
  border-radius: 5px;
}

h2 {
  font-size: 32px;
  color: #ffffff;
  margin-bottom: 10px;
}

.chart-container {
  width: calc(100% - 100px);
  height: calc(100% - 52px);
  margin: 0 auto;
  position: relative;
}

.gantt-wrap {
  width: 100%;
  height: 100%;
  color: #fff;
  display: flex;
  overflow: scroll;
}

.gantt-head {
  width: 20%;
}

.date-head {
  height: calc((16px * 2) + 10px);
}

.bottom-date {
  width: fit-content;
  border-bottom: 1px solid #fff;
}

.item-head,
.date-head {
  display: flex;
  justify-content: center;
  align-items: center;
}

.gantt-body {
  width: 100%;
  height: fit-content;
  overflow-x: scroll;
  overflow-y: visible;
}

/* .gantt-body li:nth-child(3) {
  margin-top: 50px;
}

.top-date,
.bottom-date {
  position: absolute;
}

.back {
  position: absolute;
  height: 42px;
  top: 0;
  left: 0;
  background: #2C2F3B;
  width: 100%;
}

.top-date {
  top: 5px;
}

.bottom-date {
  top: 30px;
} */

.item-box div,
.bottom-date div,
.top-date div {
  display: flex;
  width: fit-content;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.top-date {
  margin: 5px 0;
}

.month-item {
  text-align: center;
}

.gantt-item {
  background: green;
}

.gantt-item,
.day-item,
.gantt-item-space {
  width: 30px;
  height: 16px;
  text-align: center;
}

.gantt-item-space,
.day-space {
  text-indent: -9999px;
  font-size: 1px;
}

li {
  margin-bottom: 10px;
  height: 16px;
}
</style>