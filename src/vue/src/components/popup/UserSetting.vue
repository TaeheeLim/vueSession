<template>
  <ul class="setting-container">
    <li class="id">
      <span class="badge">ID</span>
      <span class="content" v-text="userDetail.id"></span>
    </li>
    <li class="nick">
      <span class="badge">NickName</span>
      <input class="content" v-model="userDetail.nickName">
    </li>
    <li class="k-tag">
      <span class="badge">K Tag</span>
      <span class="content" v-text="userDetail.kTag"></span>
    </li>
    <li class="phone">
      <span class="badge">Phone</span>
      <input class="content" v-model="userDetail.phone">
    </li>
    <li class="password">
      <span class="badge">Password</span>
      <input class="content" v-model="userDetail.password">
    </li>
    <li class="profile-img">
      <span class="img-wrap"><img src="http://placeimg.com/640/480/people" alt="userProfile"></span>
      <input type="file" class="content">
    </li>
    <li><button>modify</button></li>
  </ul>
</template>

<script>
export default {
  name: "userSetting",
  data() {
    return {
      userDetail: {
        id: "adg0807",
        nickName: "kade",
        kTag: "#aa09d7d",
        phone: "+82)10-4063-4264",
        password: "****",
        img: ""
      }
    }
  }
}
</script>

<style scoped>
.setting-container {
  background: #16161A;
  border: 1px solid #999;
  position: absolute;
  top: 70px;
  right: 0;
  width: 20vw;
  border-radius: 10px;
  z-index: 10;
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.setting-container > li {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.badge {
  background: #3F80A9;
  color: #fff;
  width: 40%;
  text-align: center;
  padding: 5px;
  border-radius: 20px;
  margin-right: 10px;
}

.img-wrap {
  width: 80px;
  text-align: center;
}

img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

button {
  color: #fff;
  margin-left: auto;
}

input {
  border: none;
  background: none;
  outline: none;
  color: #fff;
}

.content {
  border-bottom: 1px solid #999;
  width: 60%;
}
</style>