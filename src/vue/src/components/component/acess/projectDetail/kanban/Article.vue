<template>
<div class="main-container">
  <div v-if="showAddForm" class="bg-container">
    <div class="white-container">
      <div class="color-picker-wrap">
        <input
          class="create-input"
          v-model="inputBadge"
          type="text"
          placeholder="badge"
          id="inputBadge"
        />
        <div class="vertical-wrap">
          <label
            id="pickColor"
            for="color"
            :style="{ background: pickColor }"
          ></label>
          <input id="color" type="color" v-model="pickColor" />
          <CalendarIcon
            id="inputDate"
            class="icons calendar"
            @click="showCalendar()"
          />
        </div>
        <vue-cal
          locale="ko"
          class="vuecal--date-picker"
          xsmall
          hide-view-selector
          :time="false"
          :transitions="false"
          active-view="month"
          :disable-views="['years,week,days']"
          style="width: 210px; height: 230px"
          @cell-click="pickDate($event)"
          @dblclick="showCal = false"
          v-if="showCal"
        >
        </vue-cal>
      </div>

      <textarea
        class="create-input input-content"
        v-model="inputContent"
        type="text"
        placeholder="content"
        id="inputContent"
      />
      <div class="button-zone">
        <PencilAltIcon
          @click="addCard(addColumnIndex)"
          class="icons btn create-btn"
        />
        <ReplyIcon @click="closeAddForm()" class="icons btn return-btn" />
      </div>
    </div>
  </div>
   <Container
    group-name="cols"
    tag="div"
    orientation="horizontal"
    @drop="onColumnDrop($event)"
  >
    <div
      class="kanbanContainer"
      v-for="(column, index) in kanban.columns"
      :key="index"
    >
      <div>
        <div class="kanban-title">
          <span class="text-lg">{{ column.name }} </span>
          <DocumentAddIcon class="icons" @click="beforeShowAddForm(index)" />
        </div>
        <Draggable class="kanban-column">
          <Container
            orientation="vertical"
            group-name="col-items"
            :shouldAcceptDrop="(e, payload) => e.groupName === 'col-items'"
            :get-child-payload="getCardPayload(column.id)"
            :drop-placeholder="{
              className: `drop-placeholder`,
              animationDuration: '300',
              showOnTop: true,
            }"
            :dragClass="`cardGhostDrag`"
            :dropClass="`cardGhostDrop`"
            @drop="(e) => onCardDrop(column.id, e)"
          >
            <KanbanItem
              v-for="(item, cardIndex) in column.cards"
              :key="item.id"
              :columnIndex="index"
              :cardIndex="cardIndex"
              :item="item"
            />
          </Container>
        </Draggable>
      </div>
    </div>
  </Container>
</div>
</template>

<script>
import { mapMutations , mapState } from "vuex";
import moment from "moment";
import KanbanItem from "./KanbanItem.vue";
import VueCal from "vue-cal";
import "vue-cal/dist/vuecal.css";
import "vue-cal/dist/i18n/ko.js";
import {
  DocumentAddIcon,
  PencilAltIcon,
  ReplyIcon,
  CalendarIcon,
} from "@heroicons/vue/outline";
import { Container, Draggable } from "vue3-smooth-dnd";
import { applyDrag } from "@/assets/helpers";

export default {
  components: {
    Container,
    Draggable,
    KanbanItem,
    DocumentAddIcon,
    PencilAltIcon,
    ReplyIcon,
    CalendarIcon,
    VueCal,
  },
  computed: {
    ...mapState({
      showAddForm: state => state.kanban.showAddForm,
      showCal: state => state.kanban.showCal,
    }),
  },
  data() {
    return {
      openInput: -1,
      kanban: this.$store.state.kanban.kanban,
      pickColor: "#4caf50",
      addColumnIndex: "",
      inputBadge: "",
      inputContent: "",
      inputDate: "",
    };
  },
  methods: {
    ...mapMutations({
      add: "kanban/add",
      showAdd: "kanban/showAdd",
      closeAdd: "kanban/closeAdd",
      showCalendar: "kanban/showCalendar"
    }),
    pickDate(data) {
      let today = moment().format("YYYY-MM-DD HH:mm");
      let nowTime = moment().format("HH:mm:ss");

      let todayWithOutTime = moment().format("YYYY-MM-DD");
      let selectDate = moment(data.format("YYYY-MM-DD"))._i;

      let temp = `${selectDate} ${nowTime}`;

      selectDate = moment(temp, "YYYY-MM-DD HH:mm:ss");

      if (
        selectDate.from(today).split(" ")[0] !== "in" &&
        selectDate._i !== todayWithOutTime
      ) {
        let target = document.querySelector(
          ".vuecal__cell--selected .vuecal__cell-content"
        );
        target.style.background = "red";
        setTimeout(() => {
          target.style.background = "none";
        }, 1000);
        clearTimeout();
        return;
      } else {
        this.inputDate = selectDate;
      }
    },
    getColumnHeightPx() {
      let container = document.getElementById("kanbanContainer");
      return container ? container.offsetHeight - 122 : 0;
    },
    onColumnDrop(dropResult) {
      const kanban = Object.assign({}, this.kanban);
      kanban.columns = applyDrag(kanban.columns, dropResult);
      this.kanban = kanban;
    },
    onCardDrop(columnId, dropResult) {
      if (dropResult.removedIndex !== null || dropResult.addedIndex !== null) {
        const kanban = Object.assign({}, this.kanban);
        const column = kanban.columns.filter((p) => p.id === columnId)[0];
        const itemIndex = kanban.columns.indexOf(column);
        const newColumn = Object.assign({}, column);
        newColumn.cards = applyDrag(newColumn.cards, dropResult);
        kanban.columns.splice(itemIndex, 1, newColumn);
        this.kanban = kanban;
      }
    },
    getCardPayload(columnId) {
      return (index) => {
        return this.kanban.columns.filter((p) => p.id === columnId)[0].cards[
          index
        ];
      };
    },
    closeAddForm() {
      this.closeAdd();
      this.inputDate = "";
      this.inputContent = "";
      this.inputBadge = "";
    },
    beforeShowAddForm(index) {
      this.addColumnIndex = index;
      this.showAdd();
    },
    addCard(index) {
      let inputData = [this.inputBadge, this.inputContent];

      let testBeforePush = ["inputBadge", "inputContent"];

      for (let index in testBeforePush) {
        if (inputData[index] === "") {
          let target = document.getElementById(testBeforePush[index]);
          target.style.boxShadow = "0px 0px 40px 40px #dd323e";
          target.style.transition = "all 0.7s ease-in-out";
          setTimeout(() => {
            target.style.boxShadow = "none";
          }, 1000);
          clearTimeout();
          return;
        }
      }

      if (this.inputDate === "") {
        this.showCal = true;

        let inputDate = document.querySelector("svg.icons.calendar");

        inputDate.style.boxShadow = "0px 0px 40px 40px #dd323e";
        inputDate.style.transition = "all 0.7s ease-in-out";
        setTimeout(() => {
          inputDate.style.boxShadow = "none";
        }, 1000);
        clearTimeout();
        return;
      }

      let payload = [];
      payload.push(index);
      payload.push(this.inputBadge);
      payload.push(this.pickColor);
      payload.push(this.inputDate);
      payload.push(this.inputContent);
      payload.push(this.$store.state.sign.loginInfo.id);

      this.add(payload);
      this.showAddForm = false;
      this.inputBadge = "";
      this.inputDate = "";
      this.inputContent = "";
    },
  },
};
</script>
<style scoped>
.main-container {
  padding-top: 20px;
}

.bg-container {
  z-index: 20;
  height: calc(100% - 70px);
  width: 100vw;
  background: rgba(0, 0, 0, 0.36);
  position: absolute;
  color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.white-container {
  z-index: 2;
  width: 30vw;
  border-radius: 15px;
  background: #2c2f3b;
  padding: 25px;
  box-shadow: 0px 3px 7px rgba(255, 255, 255, 0.2) inset;
}

.create-input {
  background: #414556;
  border: none;
  outline: none;
  width: 70%;
  margin-bottom: 15px;
  font-size: 22px;
  color: white;
  border-radius: 5px;
  padding: 5px 10px;
  box-shadow: 0px 5px 35px rgba(0, 0, 0, 0.5);
}

.color-picker-wrap {
  position: relative;
  /* width: fit-content; */
  display: flex;
  justify-content: space-between;
}

#color {
  z-index: -9999;
  width: 0;
  height: 0;
  top: 0;
  right: 0;
  position: absolute;
  visibility: hidden;
}

[for="color"] {
  width: 25px;
  height: 25px;
  margin-left: 20px;
  border-radius: 5px;
  box-shadow: 0 5px 35px rgba(0, 0, 0, 0.3);
}

.vuecal--date-picker {
  position: absolute;
  top: 20%;
  left: 100%;
  background: #2c2f3b;
  border-radius: 15px;
}

.smooth-dnd-container.horizontal {
  display: flex !important;
  width: 100vw;
  height: calc(100vh - 70px);
  justify-content: space-around;
}

.kanbanContainer {
  display: flex;
  padding: 5px;
  width: 18%;
}

.kanbanContainer > * {
  width: 100%;
}

.kanbanContainer > * > *:last-child {
  max-height: 86%;
  overflow-y: scroll;
}

.kanban-card-container {
  cursor: move;
  margin-top: 4px;
  margin-bottom: 8px;
}

.kanban-title {
  height: 40px;
  width: 100%;
  margin-bottom: 10px;
  background-color: #2c2f3b;
  border-radius: 15px;
  position: relative;
  font-size: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}
.vertical-wrap {
  display: flex;
  justify-content: right;
  align-items: center;
  width: 30%;
}

.input-content {
  width: 100%;
  height: 200px;
  resize: none;
  font-weight: bold;
  margin-bottom: 15px;
  font-size: 22px;
  color: white;
  border-radius: 5px;
  padding: 5px 10px;
}

.kanban-title .icons {
  position: absolute;
  right: 10%;
  top: 10%;
  width: 22px;
  cursor: pointer;
}

.icons {
  width: 20px;
  vertical-align: sub;
  display: inline-block;
}

.calendar {
  border-radius: 5px;
  box-shadow: 0 5px 35px rgba(0, 0, 0, 0.3);
  width: 28px;
  height: 28px;
  margin: 0;
  /* margin:5px 15px 5px 0; */
}

.button-zone {
  text-align: center;
  display: flex;
  justify-content: space-between;
}

.btn {
  color: #fff;
  border-radius: 8px;
  width: 110px;
  height: 50px;
  box-shadow: 0px 5px 12px rgba(255, 255, 255, 0.2) inset;
}

.create-btn {
  background: #ff8906;
}

.return-btn {
  background: #bd1e19;
}

.kanban-title span {
  vertical-align: -webkit-baseline-middle;
}

.kanban-column {
  background-color: #2c2f3b;
  margin-bottom: 10px;
  border-radius: 15px;
  width: 100%;
  box-sizing: content-box;
  overflow-y: scroll;
  max-height: 830px;
}

.spin {
  animation: spin 3s linear infinite;
}

@keyframes spin {
  100% {
    transform: rotate(360deg);
  }
}

::-webkit-scrollbar {
  width: 0px;
  height: 5px;
  /* border-width: initial;
  border-style: none;
  border-color: initial;
  border-image: initial; */
}
.dropPlaceholder {
  border: 2px dotted #ff8906;
  border-radius: 50px;
}

.cardGhostDrag {
  transform: rotate(5deg) scale(1.1);
  transition: all 0.2s ease-in;
  border-radius: 50px;
  color: white;
}
.cardGhostDrop {
  transition: all 5s ease-in;
  transform: rotate(-20deg) scale(0.9);
}

.kanban-input-err {
  border: 1px solid #bd1e19;
  color: red;
}
</style>