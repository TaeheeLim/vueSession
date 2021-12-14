<template>
  <div class="notice-container">
    <div
      :id="`notice-${index}`"
      class="notice-box"
      v-for="(message, index) in messages"
      :key="index"
    >
      <p class="notice-text">
        {{ message.user }} {{ message.content }}
        <button @click="closeNotice(index)" class="notice-close-btn">
          <i class="fas fa-times"> </i>
        </button>
      </p>
      <div class="notice-bar">
        <div class="notice-bar-fill"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  updated() {
    for (let index in this.messages) {
      setTimeout(() => {
        document.querySelector(`#notice-${index}`).style.display = "none";
      }, 3900);
    }
    clearTimeout();
  },
  data() {
    return {
      messages: [],
    };
  },
  methods: {
    closeNotice(index) {
      document.querySelector(`#notice-${index}`).style.display = "none";
    },
    noticeTest() {
      this.messages.push({
        user: "zerochae",
        content: "님이 로그인하였습니다",
      });
    },
  },
};
</script>

<style>
.notice-container {
  position: absolute;
  right: 0;
  top:70px;
  display: flex;
  flex-direction: column;
  z-index: 10;
}
.notice-box {
  margin-bottom: 10px;
  width: 280px;
  height: 60px;
  right: 0;
  background: #414556;
  display: flex;
  align-items: center;
  position: relative;
  flex-direction: column;
  justify-content: flex-end;
  border-radius: 5px;
  transform: translate(-100%);
  animation: slide 4s linear;
  box-shadow: 3px 5px 16px rgba(255, 255, 255, 0.2) inset;
  overflow: hidden;
}

.notice-text {
  margin: 5px 5px 20px 5px;
}

.notice-box i {
  position: absolute;
  top: 0;
  right: 2%;
}

.notice-box button{
  color:white
}

.notice-bar {
  height: 7px;
  width: 278px;
  background: #fff;
  position: absolute;
  bottom: 0;
}

.notice-bar-fill {
  background: #3f80a9;
  width: 100%;
  height: 100%;
  animation: progress 3s linear;
  box-shadow: 2px 2px 3px rgba(255, 255, 255, 0.3) inset;
}

@keyframes progress {
  0% {
    width: 0%;
  }
  100% {
    width: 100%;
  }
}

@keyframes slide {
  0% {
    transform: translate(100%);
  }
  9%,
  80% {
    transform: translate(0%);
  }
  90%,
  100% {
    transform: translate(100%);
  }
}
</style>