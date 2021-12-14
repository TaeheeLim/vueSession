<template>
<div class="list-container">
    <div id="modal" v-if="this.IsModalOpen === true" >
        <Modal/>
    </div>
    <div class="project-state">
        <div id="project-top">
            <div id="img-div">
                <img id="profile-img" src="@/assets/profile.png" @click="search">
            </div>
            <div id="nick-div">
                KADE
            </div>
        </div>
        <div id="profile-list">
            <div>
                <div>
                    Proceddings( {{ progressList.length }} )
                </div>
                <div class="projects-div-ul">
                    <ul class="projects-ul">
                        <li v-for="item in progressList" :key="item">
                            <span>
                                <i class="far fa-circle"></i>
                            </span>
                            <span class="test-span">
                                <router-link to="/pdtail/dashboard">{{ item.name }}</router-link>
                            </span>
                        </li>
                    </ul>
                </div>
            </div>
            <div>
                <div class="project-completed">
                    Completed( {{ completeList.length }} )
                </div>
                <div class="projects-div-ul">
                    <div>
                        <ul class="projects-ul">
                            <li v-for="item in completeList" :key="item">
                                <span>
                                    <i class="far fa-circle"></i>
                                </span>
                                <span class="test-span">
                                    <router-link to="/pdtail/dashboard">{{ item.name }} </router-link>
                                </span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="list-right-container">
        <div id="addproject-div">
            <AddProjects/>
        </div>
        <div id="changes-div">
            <Changes/>
        </div>
    </div>
</div>
</template>

<script>
import AddProjects from '@/components/component/acess/projects/AddProjects.vue'
import Changes from '@/components/component/acess/projects/LatestChanges.vue'
import Modal from '@/components/popup/CreateProject.vue'
import { mapState } from 'vuex'
export default {
    name : 'ProjectList',
    data() {
        return {
            projectList : [],
            progressList : [],
            completeList : [],
        }
    },
    computed : {
        ...mapState({
            IsModalOpen : state => state.projectList.IsModalOpen
        })
    },

    methods: {
        getProjectsList(){
            this.axios.get('/projectDetail.json').then(e => {
                this.projectList = e.data
                for(let i = 0; i < this.projectList.length; i++){
                    if(this.projectList[i].isComplete === 'N'){
                        this.progressList.push(this.projectList[i])
                    } else {
                        this.completeList.push(this.projectList[i])
                    }
                }
            })

        }
    },
    components : {
        AddProjects,
        Changes,
        Modal,
    },
    mounted() {
        this.getProjectsList()
    },
}
</script>

<style scoped>
.list-container {
    color : white;
    display: flex;
    height: calc(100vh - 70px);
}

.list-top-container {
    margin-top: 20px;
}

#profile-img {
    width : 50px;
    height: 50px
}
#nick-div {
    margin-left: 16px;
    height: 50px;
    width: fit-content;
    font-size: 30px;
    padding-top: 10px;
}

.project-state {
    width: 10vw;
    padding-top: 20px;
    padding-left: 30px;
    display: flex;
    flex-direction: column;
}

#profile-list {
    display: flex;
    flex-direction: column;
    margin-top: 30px;
}

#project-top {
    display: flex;
    padding-top : 40px;
}

.projects-div-ul {
    margin-left: 20px;
    margin-top : 8px;
    height: 140px;
    overflow: scroll;
    -ms-overflow-style: none;
    scrollbar-width: none; 
}

.projects-div-ul::-webkit-scrollbar {
    display: none;
}

#completed-div {
    margin-top: 10px;
}

.projects-ul a {
    color: #fff;
}

.test-span {
    margin-left: 10px;
}

.projects-ul li {
    margin-top: 10px;
}

.project-completed {
    margin-top: 10px;
}

.list-right-container {
    display: flex;
    flex-direction: column;
    margin-left: 20px;
    width: 90%;
    height: calc(100vh - 70px);
}

#addproject-div {
    height: 60%;
}

#changes-div {
    height: 40%;
    font-size: 30px;
    width: 100%;
    padding-left: 60px;
}
</style>