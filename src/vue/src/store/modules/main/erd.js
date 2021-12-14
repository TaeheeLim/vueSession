import erdData from '../../../assets/erdData.json'

const erd = {
  namespaced: true,
  state: {
    scale: 1,
    lastIdx: 3,
    erdData: erdData,
    relation: {
      primaryKey: {},
      foreignKey: {}
    },
    exportQuery: "",
    isShowQuery: false,
    sideBarData: {
      topData: [],
      title: "ERD",
      path: "/pdtail/erdview/erd",
      textColor: "#fff",
    },
    writeData: {
      popupName: "addTable",
      isOpen: false,
      tableData: {
        idx: 0,
        name: "",
        columns: [
          {
            table: "",
            name: "",
            type: "",
            constraint: "",
            references: null
          }
        ],
        isModify: false
      }
    },
  },
  mutations: {
    // 테이블 생성 창 열고 닫기
    openAndClose(state) {
      state.writeData.isOpen = !state.writeData.isOpen
      state.writeData.popupName = state.writeData.isOpen ? "close" : "addTable"
      if(state.writeData.popupName === "addTable") {
        state.writeData.tableData = {
          name: "",
          columns: [
            {
              name: "",
              type: "",
              constraint: ""
            }
          ]
        }
      }
    },
    // 테이블 생성 창에서 컬럼 추가
    addCol(state) {
      state.writeData.tableData.columns.push({
        name: "",
        type: "",
        constraint: ""
      })
    },
    // 테이블 생성 창에서 컬럼 삭제
    delCol(state) {
      state.writeData.tableData.columns.pop()
    },
    // 테이블 생성
    createTable(state) {
      const table = state.writeData.tableData
      let isDuplicate = false
      state.sideBarData.topData.forEach(item => {
        if(item.name === table.name) {
          state.writeData.tableData = {
            idx: 0,
            name: "",
            columns: [
              {
                table: "",
                name: "",
                type: "",
                constraint: "",
                references: null
              }
            ],
            isModify: false
          }
          state.writeData.isOpen = false
          state.writeData.popupName = "addTable"
          isDuplicate = true
          alert("중복 테이블 작성은 불가능 합니다.")
        }
      })
      if(isDuplicate) {
        return
      }
      table['idx'] = ++state.lastIdx
      table.columns.forEach(item => {
        item["table"] = table.name
      })
      state.sideBarData.topData.push(table)
      state.writeData.tableData = {
        idx: 0,
        name: "",
        columns: [
          {
            table: "",
            name: "",
            type: "",
            constraint: "",
            references: null
          }
        ],
        isModify: false
      }
      state.writeData.isOpen = false
      state.writeData.popupName = "addTable"
    },
    // 테이블 삭제
    deleteTable(state, item) {
      delete state.relation.primaryKey[item.idx]
      delete state.relation.foreignKey[item.idx]

      const idx = state.sideBarData.topData.indexOf(item)
      state.sideBarData.topData.splice(idx, 1)
    },
    // 테이블 수정폼 출력
    showModify(state, item) {
      state.sideBarData.topData.find(ele => ele.idx === item.idx).isModify = true
    },
    // 테이블 수정 완료
    modifyTable(state, item) {
      // axios로 백단으로 쏴서 저장

      state.sideBarData.topData.find(ele => ele.idx === item.idx).isModify = false
    },
    // 백에서 가져온 데이터 바인딩
    addErdData(state) {
      state.sideBarData.topData = JSON.parse(JSON.stringify(state.erdData))
    },
    // 가져온 데이터를 바탕으로 관계 json설정
    setRelation(state) {
      const primary = {}
      const foreign = {}
      const tables = JSON.parse(JSON.stringify(state.sideBarData.topData))
      
      for(let item of tables) {
        const pk = []
        const references = new Set()
        const tableIdx = item.idx
        
        for(let col of item.columns) {
          if(col.constraint === 'pk') {
            pk.push(col)
          }
          if(col.references !== null) {
            let parentId = null
            tables.forEach(ele => {
              if(ele.name === col.references) {
                parentId = ele.idx
              }
            })
            references.add(parentId)
          }
        }

        primary[tableIdx] = pk
        foreign[tableIdx] = Array.from(references)
      }

      state.relation["primaryKey"] = primary
      state.relation["foreignKey"] = foreign
    },
    // 쿼리 출력
    exportQuery(state) {
      const tables = state.sideBarData.topData
      const primary = JSON.parse(JSON.stringify(state.relation.primaryKey))
      const foreign = JSON.parse(JSON.stringify(state.relation.foreignKey))
      // 테이블 생성
      const createTable = []
      for(let table of tables) {
        let sql = `create table ${table.name} (`

        for(let col of table.columns) {
          sql += `${col.name} ${col.type}, `
        }
        
        sql = sql.substring(0, sql.length - 2) + ");"
        createTable.push(sql)
      }

      // 기본키 생성
      const createPrimary = []
      for(let key of Object.keys(primary)) {
        if(primary[key].length === 0) {
          continue
        }
        const customId = `pk_${primary[key][0].table}`
        let sql = `alter table ${primary[key][0].table} constraint `
        sql += `${customId} add primary key(`
        
        for(let item of primary[key]) {
          sql += `${item.name}, `
        }
        sql = sql.substring(0, sql.length - 2) + `);`
        createPrimary.push(sql)
      }

      //외래키 생성
      const createForeign = []
      for(let key of Object.keys(foreign)) {
        const parentKey = foreign[key]
        if(parentKey.length === 0) {
          continue
        }
        let tableName = ""
        let parentTable = ""
        let primaryKey = ''
        let sql = ''
        
        for(let i of parentKey) {
          sql = ''
          primaryKey = ''
          state.sideBarData.topData.forEach(table => {
            if(table.idx == key) {
              tableName = table.name
            }
            if(table.idx == i) {
              parentTable = table.name
            }
          })
          if(state.relation["primaryKey"][i] !== undefined) {
            state.relation["primaryKey"][i].forEach(ele => {
              primaryKey += `${ele.name}, `
            })
          }

          primaryKey = primaryKey.substring(0, primaryKey.length - 2)
          sql = `alter table ${tableName} add constraint fk_${parentTable}_${tableName} foreign key(`
          sql += `${primaryKey})`
          sql = `${sql} references ${parentTable} (${primaryKey});`
          createForeign.push(sql)
        }
      }

      let query = ``
      createTable.forEach(item => {
        query += `${item}\n`
      })
      query += `\n`
      createPrimary.forEach(item => {
        query += `${item}\n`
      })
      query += `\n`
      createForeign.forEach(item => {
        query += `${item}\n`
      })

      state.exportQuery = query
      state.isShowQuery = true
    },
    closeExportQuery(state) { 
      state.isShowQuery = false
    },
    clickErd(state, item) {
      state.sideBarData.topData.forEach(item => {
        item.borderColor = ""
      })
      if(item.isClick) {
        item.isClick = false
        return
      }

      item.borderColor = "1px solid red"
      item.isClick = true
      item.columns.forEach(item => {
        if(item.references !== "") {
          state.sideBarData.topData.forEach(parent => {
            if(parent.name === item.references) {
              parent.borderColor = "1px solid blue"
            }
          })
        }
      })
    },
    zoomIn(state) {
      if(state.scale >= 1) {
        return
      }
      state.scale += .1
    },
    zoomOut(state) {
      if(state.scale < .6) {
        return
      }
      state.scale -= .1
    }
  },
  actions: {
    getErdData(context) {
      context.commit('addErdData')
      context.commit('setRelation')
    }
  }
}

export default erd