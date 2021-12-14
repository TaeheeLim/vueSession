<template>
<div id="change-head">
    Latest Changes
</div>
<div id="change-list" @mousewheel="wheel">
    <div class="list-detail" v-for="(a,i) in 50" :key="i">
        <div class="list-wrapper">
            <router-link id="prjct-name" to="/pdtail/dashboard">
                Test Project
            </router-link>
            <div class="content-children">
                <router-link to="/pdtail/dashboard">Issue</router-link>
                <router-link to="/pdtail/dashboard">치명적인 버그 발견!</router-link>
                <router-link to="/pdtail/dashboard">치명적인 버그 발견!</router-link>
            </div>
            <div class="content-children">
                <router-link to="/pdtail/dashboard">Schedule</router-link>
                <router-link to="/pdtail/dashboard">21-01-01 신년회</router-link>
                <router-link to="/pdtail/dashboard">21-01-01 신년회</router-link>
                <router-link to="/pdtail/dashboard">21-01-01 신년회</router-link>
            </div>
        </div>
    </div>
</div>
</template>

<script>
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
        }
        
    },
    methods: {
        //가로 스크롤 ^^
        wheel(e){
            let target = document.querySelector('#change-list')
            
            var a = target.scrollWidth - (target.scrollLeft + target.offsetWidth)

            let wheelDelta = e.wheelDelta;
            target.scroll
            if(wheelDelta > 0 && a + target.offsetWidth < target.scrollWidth){
                console.log("여기 왼쪽 맞지")
                target.scrollLeft = (wheelDelta * this.countLeft);
                this.countLeft--;
                this.countRight++;
            } else if (wheelDelta < 0 && target.offsetWidth + target.scrollLeft < target.scrollWidth) {
                target.scrollLeft = (wheelDelta * this.countRight);
                this.countRight--;
                this.countLeft++;
            }
        }
    },
    
    mounted() {
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

.content-children > a:first-child{
    font-size: 18px;
}

.content-children > a:nth-child(2){
    font-size : 15px;
    padding-left: 10px;
}

.content-children > a:nth-child(3){
    font-size : 15px;
    padding-left: 10px;
}

.content-children > a:last-child{
    font-size : 15px;
    padding-left: 10px;
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



</style>