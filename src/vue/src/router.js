import { createWebHistory, createRouter } from "vue-router"

// import global from './routers/global'
import Home from './components/container/noAccess/Home.vue'
import Demo from './components/container/noAccess/Demo.vue'

import Community from './components/container/noAccess/Community.vue'
import Free from './components/component/noAccess/Community/Free.vue'
import QnA from './components/component/noAccess/Community/QnA.vue'

import Sign from './components/container/noAccess/Sign.vue'
import Projects from './components/container/access/Projects.vue'

import ProjectDetail from './components/container/access/projectDetail/ProjectDetail.vue'
import DashBoard from './components/container/access/projectDetail/DashBoard.vue'
import Scheduler from './components/container/access/projectDetail/Scheduler.vue'

import Compiler from './components/container/access/projectDetail/compiler/Compiler.vue'
import Java from './components/container/access/projectDetail/compiler/Java.vue'
import HTML from './components/container/access/projectDetail/compiler/HTML.vue'

import Board from './components/container/access/projectDetail/ProjectBoard.vue'
import Demand from './components/container/access/projectDetail/Demand.vue'
import Gantt from './components/container/access/projectDetail/GanttChart.vue'
import GitAndIssue from './components/container/access/projectDetail/GitAndIssue.vue'

import Kanban from './components/container/access/projectDetail/Kanban.vue'

import ErdAndView from './components/container/access/projectDetail/ErdAndView.vue'
import Table from './components/component/acess/projectDetail/erdAndView/erd/Table.vue'

import Setting from './components/container/access/projectDetail/Setting.vue'



const routes = [
  {
    path: '/',
    component: Home,
  },
  {
    path: '/community',
    component: Community,
    children: [
      {
        path: "free",
        component: Free
      },
      {
        path: "qna",
        component: QnA
      },
    ]
  },
  {
    path: '/signin',
    component: Sign,
  },
  {
    path: '/demo',
    component: Demo
  },
  {
    path: '/projects',
    component: Projects,
  },
  {
    path: "/pdtail",
    component: ProjectDetail,
    children: [
      {
        path: "dashboard",
        component: DashBoard
      },
      {
        path: "scheduler",
        component: Scheduler
      },
      {
        path: "compiler",
        component: Compiler,
        children: [
          {
            path: "backend",
            component: Java
          },
          {
            path: "frontend",
            component: HTML
          }
        ]
      },
      {
        path: "demand",
        component: Demand
      },
      {
        path: "board",
        component: Board
      },
      {
        path: "gantt",
        component: Gantt
      },
      {
        path: "gitissue",
        component: GitAndIssue
      },
      {
        path: "kanban",
        component: Kanban,
        children: [
          
        ]
      },
      {
        path: "erdview",
        component: ErdAndView,
        children: [
          {
            path: "erd",
            component: Table
          }
        ]
      },
      {
        path: "setting",
        component: Setting
      },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router;