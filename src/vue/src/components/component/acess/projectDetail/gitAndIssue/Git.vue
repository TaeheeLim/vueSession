<template>
    <div class="Repo">
        <h1>Project Repository</h1>
        <div class="nodeTree">
            <h1>Document List</h1>
            <div class="repoContent">
                <Tree 
                :search-text="searchText"
                :use-icon="true"
                @nodeExpanded="onUpdate"
                :nodes="data" 
                />
            </div>
        </div>
    </div>
</template>
 
<script>
import { mapMutations } from 'vuex'
import { mapActions } from 'vuex'
import { ref } from 'vue';
import Tree from 'vue3-tree'
import "vue3-tree/dist/style.css";

const key = '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';

export default {
    components : {
        Tree,
    },
    data() {
        return {
            data : ref([]),
            searchText : ref(''),
            encodedData : '',
        }
    },
    methods: {
        ...mapMutations({
            setDecodeData : 'git/setDecodeData',
            setSelectedFileName : 'git/setSelectedFileName',
        }),
        ...mapActions({
            getRepoList : 'git/getRepoList',
        }),
        
        onUpdate(e) {
            if(e.type === 'file'){
                this.sendContent(e)
                return
            }
            this.axios.get(`${e.url}`, {
                    headers : {
                        Authorization : `token ${key}`
                    }
            })
            .then( res => {
                for(let i of res.data){
                    const a = {
                        idx : i.sha,
                        label : i.name,
                        type : i.type,
                        url : i.url,
                        nodes : [],
                        content : null,
                    }
                    if(i.type === 'file'){
                        a.nodes = null
                        a.content = i.content
                    }

                    e.nodes.push(a)
                }
            })
        },

        sendContent(e){
                this.axios.get(`${e.url}`, {
                        headers : {
                            Authorization : `token ${key}`
                        }
                })
                .then( res => {
                    this.encodedData = res.data.content
                    this.decodeData()
                    this.setSelectedFileName(res.data.name)
                })
        },

        getFileList() {
            this.axios.get('https://api.github.com/repos/Juwon-Yun/kanboo_my_work/contents', {
                    headers : {
                        Authorization : `token ${key}`
                    }
                })
            .then( res =>{
                for(let i of res.data){

                    // 403 error 방지 
                    if(i.name === 'package-lock.json' ){
                        continue
                    }

                    const array = {
                        idx : i.sha,
                        label : i.name,
                        type : i.type,
                        url : i.url,
                        nodes : [],
                        content : null,
                    }
                    if(i.type === 'file'){
                        array.nodes = null
                        array.content = i.content
                    }
                    this.data.push(array)
                }// for i of
            })
        },

        decodeData(){
            this.setDecodeData( decodeURIComponent(escape(window.atob(this.encodedData)))) 
        },
    },
    
    mounted() {
        this.getFileList();
    },
}

</script>
 
<style scoped>
/* 
.tree-row-item {
    display: flex;
    align-items: center;
    position: relative;
    padding: 0px 10px
}
    margin-top: 4px;
    height: 15px;

    vue3-tree/dist/style.css 로 가서 변경해줘야 함


*/
.Repo > h1:nth-child(1){
    margin-bottom: 3px;
}
.nodeTree{
    height: 20vh;
    background-color: #2C2F3B;
    overflow: scroll;
    -ms-overflow-style: none;
    scrollbar-width: none;
}
.nodeTree::-webkit-scrollbar {
    display: none; 
}
.nodeTree > h1{
    padding-bottom: 5px;
}
.repoContent{
    color: #eee;
}

.tree-row-item{
    padding: 0;
}
.tree-list{
    gap : 5px;
}
</style>