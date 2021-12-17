<template>
  <div class="admin-container">
    <div class="middle">
    <UserProject class="userProject-container"/>
    <UserList class="userList-container"/>
    </div>
    <QnA class="qna-container"/>
  </div>
</template>

<script>
import QnA from "../../component/admin/QnA.vue";
import UserList from "../../component/admin/UserList.vue";
import UserProject from "../../component/admin/UserProject.vue";
export default {
  components: { 
    QnA, 
    UserList, 
    UserProject 
    },
};
</script>

<style>

.admin-container{
height: calc(100vh - 70px);
display: flex;
padding: 25px;
}

.userProject-container{
  width: 50vw;
  height: 50%;
  padding: 25px;
  overflow: hidden;
}

.userList-container{
  width: 50vw;
  height: 50%;
  padding: 25px;
  overflow: hidden;
}

.qna-container{
  width: 50vw;
  height: 100%;
  padding: 25px;
  overflow: hidden;
}
</style>