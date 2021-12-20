<template>
<div id="change-head">
    Latest Changes
</div>
<div id="contain">
<div id="change-list" @mousewheel="wheel">
    <div v-for="item in projectList" :key="item">
    <div class="list-detail" v-if="item.issueList.length !== 0 || item.calendarList.length !== 0">
        <div class="list-wrapper">
            <router-link id="prjct-name" to="/pdtail/dashboard">
                {{ item.prjctNm }}
            </router-link>
            <div class="content-children" v-if="item.issueList.length !== 0">
                <router-link to="/pdtail/dashboard">
                    Issue
                </router-link>
                <router-link class="balloon-wrap" to="/pdtail/dashboard" v-for="issue in item.issueList" :key="issue">
                    <div class="title-wrap" v-if="issue.issueCn.length >= 10">{{ `${issue.issueCn.substring(0, 10)}...` }}</div>
                    <div class="title-wrap" v-else>{{ issue.issueCn }}</div>
                    <div class="balloon">
                        <div>
                            <span>{{ issue.issueDate }}</span>
                            <span>{{ issue.issueState }}</span>
                        </div>
                        <div>
                            {{ issue.issueCn }}
                        </div>
                    </div>
                </router-link>
            </div>
            <div class="content-children" v-if="item.calendarList.length !== 0">
                <router-link to="/pdtail/dashboard">
                    Schedule
                </router-link>
                <router-link class="balloon-wrap" to="/pdtail/dashboard" v-for="calendar in item.calendarList" :key="calendar">
                    <div class="title-wrap" v-if="calendar.calCn.length >= 10">{{ `${calendar.calCn.substring(0, 10)}...` }}</div>
                    <div class="title-wrap" v-else>{{ calendar.calCn }}</div>
                    <div class="balloon">
                        <div>
                            {{ calendar.calStartDate }}
                        </div>
                    </div>
                </router-link>
            </div>
        </div>
    </div>
    </div>
</div>
</div>
</template>

<script>
import {mapState} from "vuex";

export default {
    name : 'LatestChanges',
    data() {
        return {
            color: '#fff',
            scroll: 0,
            offset: 0,
            boxInnerWidth: 0,
            countLeft: -1,
            countRight: -1,
            changesList : [],
        }
        
    },
    computed: {
      ...mapState({
        projectList: state => state.projectList.projectList
      })
    },
    methods: {
        //가로 스크롤 ^^
        wheel(e){
            let target = document.querySelector('#change-list')

            let wheelDelta = e.wheelDelta;
            if(wheelDelta > 0 && target.scrollLeft !== 0){
                target.scrollLeft = (wheelDelta * this.countLeft);
                this.countLeft--;
                this.countRight++;
            } else if (wheelDelta < 0 && target.offsetWidth + target.scrollLeft < target.scrollWidth) {
                target.scrollLeft = (wheelDelta * this.countRight);
                this.countRight--;
                this.countLeft++;
            }
        },
        getChanges(){
          //axios address mapping
            // this.axios.get('/projectDetail.json').then(e => {
            //     this.changesList = e.data
            // })
        }
    },
    
    mounted() {
        this.getChanges()
    }
}
</script>

<style scoped>
#change-list {
    width: 100%;
    display: flex;
    flex-direction: row;
    margin-top: 30px;
    gap: 60px;
    overflow: scroll;
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
}

#change-list::-webkit-scrollbar {
    display: none;
}

.list-detail {
    border-radius: 10px;
    width: 240px; 
    height: 260px;
    font-size: 20px;
    background-color: #2C2F3B;
    padding: 10px;
    transition: ease-in-out 0.3s;
    
}

.list-wrapper > div:first-child {
    border-bottom: 1px solid #717790;
    color: #717790;
    padding-bottom: 10px;
    font-size: 22px;
}

.list-wrapper > div:nth-child(2){
    padding-top: 10px;
    padding-bottom: 10px;
}

.list-wrapper {
    width: 180px;
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.content-children {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

#prjct-name {
    border-bottom: 1px solid #717790;
    padding-bottom: 10px;
}


.content-children > a {
    font-size : 15px;
    padding-left: 10px;
}

.content-children > a:first-child {
    font-size: 18px;
}

a{
    color: #717790;
}

.list-detail:hover {
    background-color: black;
}

.list-detail:hover a {
    color:#fff;
}

.list-detail:hover #prjct-name {
    border-bottom: 1px solid darkorchid;
}

#contain {
    padding-right: 60px;
}    

.balloon-wrap {
    position: relative;
}

.balloon {
    position: absolute;
    max-width: fit-content;
    min-width: 200px;
    top: -150%;
    right: -120%;
    display: none;
    z-index: 10;
    background: #3F80A9;
    padding: 10px;
    border-radius: 5px;
    color: #fff;
}

.balloon * {
    line-height: 1.2;
}

.balloon::after {
    content: " ";
    position: absolute;
    border-style: solid;
    border-width: 0 16px 20px 17.5px;
    border-color: #3F80A9 transparent;
    display: block;
    width: 0;
    z-index: 10;
    transform: rotate(-90deg) scale(.5) translate(-50%, -50%);
    left: -8%;
    top: 10%;
}

a:hover .balloon{
    display: flex;
    flex-direction: column;
    
}

.title-wrap {
    color: #717790;
    transition: color .2s ease-in;
}

.title-wrap:hover {
    color: #fff;
}

</style>