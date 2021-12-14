import moment from "moment"

const git = {
    namespaced : true,
    state(){
        return {
            origin: {
                fileName: '',
                content : '',
                nickname : '윤주원',
                startDate : moment().format('YYYY-MM-DD HH:mm'),
                currentTime : '오늘',
                state : '버그',
            },
            issues:[
                {
                    fileName:'12345.vue',
                    content:'vue컴포넌트 에러났음',
                    nickname:'윤주원',
                    startDate:'2021-11-29',
                    currentTime:'오늘',
                    state:'버그',
                },{
                    fileName:'12345.vue',
                    content:'vue컴포넌트 에러났음',
                    nickname:'윤주원',
                    startDate:'2021-11-29',
                    currentTime:'오늘',
                    state:'수정중',
                },{
                    fileName:'12345.vue',
                    content:'vue컴포넌트 에러났음',
                    nickname:'윤주원',
                    startDate:'2021-11-29',
                    currentTime:'오늘',
                    state:'완료',
                },
                {
                    fileName:'12345.vue',
                    content:'vue컴포넌트 에러났음',
                    nickname:'윤주원',
                    startDate:'2021-11-29',
                    currentTime:'오늘',
                    state:'수정중',
                },
                {
                    fileName:'12345.vue',
                    content:'vue컴포넌트 에러났음',
                    nickname:'윤주원',
                    startDate:'2021-11-29',
                    currentTime:'오늘',
                    state:'수정중',
                },
                {
                    fileName:'ddit.vue',
                    content:'vue컴포넌트 에러났음',
                    nickname:'권영채',
                    startDate:'2021-11-29',
                    currentTime:'오늘',
                    state:'수정중',
                },
                {
                    fileName:'12345.vue',
                    content:'vue컴포넌트 에러났음',
                    nickname:'윤주원',
                    startDate:'2021-11-29',
                    currentTime:'오늘',
                    state:'수정중',
                },
                {
                    fileName:'12345.vue',
                    content:'vue컴포넌트 에러났음',
                    nickname:'윤주원',
                    startDate:'2021-11-29',
                    currentTime:'오늘',
                    state:'수정중',
                },
                {
                    fileName:'12345.vue',
                    content:'vue컴포넌트 에러났음',
                    nickname:'윤주원',
                    startDate:'2021-11-29',
                    currentTime:'오늘',
                    state:'수정중',
                },
            ],
            decodeData : '',
            selectedFileName : '',
        }
    },
    mutations :{
        setDecodeData(state, data){
            state.decodeData = data
        },

        getDecodeData(state){
            return state.decodeData
        },

        setSelectedFileName(state, data){
            state.selectedFileName = data
        },

        addIssueData(state){
            state.origin.fileName = state.selectedFileName
            state.issues.unshift(state.origin)
            state.origin = {
                fileName: '',
                content : '',
                nickname : '윤주원',
                startDate : moment().format('YYYY-MM-DD HH:mm'),
                currentTime : '오늘',
                state : '버그',
            }
        }
    },

    actions:{
    },

}

export default git;