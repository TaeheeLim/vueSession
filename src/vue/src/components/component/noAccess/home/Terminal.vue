<template>
  <div class="terminal-container" @click="focus()">
    <div class="console">
      <div class="output-text" v-for="(i, j) in consoleText.length" :key="j">
        <span class="console-text">{{ consoleText[i - 1] }}</span>
        <span class="console-text">{{ modeText[i - 1] }}</span>
        <span :class="classData[i - 1]" :id="`text-${i - 1}`" class="user-text">
          {{ enterText[i - 1] }}</span
        >
        <span class="copy" v-if="printToken && j === tokenPrintIndex" @click="copyToken"> [copy]</span>
      </div>
      <div class="userInput">
        {{ rootText }}
        <span style="color: white" v-if="inputData.length == 6"> +82) </span>
        <input
          :type="inputType"
          @keyup.enter="enter"
          id="inputBox"
          v-model="inputText"
          :class="{
            'text-color-red': signReg,
            'text-color-orange': reservedWord,
          }"
        />
        <span v-if="idCheck"> ID already registered </span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";
import signHelp from "@/assets/signHelp.js";

export default {
  name: "Terminal",
  computed: {
    ...mapState({
      loginInfo: (state) => state.sign.loginInfo,
      signInfo: (state) => state.sign.signInfo,
      findInfo: (state) => state.sign.signInfo,
    }),
  },
  updated() {
    // this.copyToken();

    if(this.printToken){
        this.tokenPrintIndex = this.consoleText.length -1 ; 
    }

  },
  mounted() {},
  components: {},
  data() {
    return {
      rootText: `Kanboo bash(base console) > `,
      modeText: [`(base console) > `],
      consoleText: [`Kanboo bash`],
      enterText: [`Choose Menu 1.login 2.sign 3.find 4.clear 5.cd.. 6.cd home`],
      classData: [`com`],
      inputType: "text",
      inputData: [],
      inputText: "",
      signHelp: signHelp.en,
      idCheck: false,
      signReg: false,
      reservedWord: false,
      printToken: false,
      tokenText: "",
      tokenPrintIndex: "",
    };
  },
  watch: {
    inputText: ["regex", "isExistId"],
  },
  methods: {
    ...mapMutations({
      login: "sign/login",
      sign: "sign/sign",
      find: "sign/find",
    }),
    isExistId() {
      let header = null;

      if (
        this.inputData.length === 1 &&
        this.inputText.length > 5 &&
        this.inputData[0] === "sign"
      ) {
        this.axios
          .post(`http://localhost:8099/access/idCheck`, header, {
            params: {
              memId: `${this.inputText}`,
            },
          })
          .then((data) => {
            if (data.data) {
              this.idCheck = true;
              this.signReg = true;
            } else {
              this.idCheck = false;
              this.signReg = false;
            }
          });
      }
    },
    enter() {
      let data = this.inputText.toLowerCase();
      let originalData = this.inputText;
      if (data === "clear") {
        this.consoleText = [`Kanboo bash`];
        this.classData = [`com`];
        switch (this.inputData[0]) {
          case "login":
            this.modeText = [`(${this.inputData[0]} console) > `];
            if (this.inputData.length == 1) {
              this.enterText = [`Enter your ID`];
            } else if (this.inputData.length == 2) {
              this.enterText = [`Enter your Password`];
            }
            this.addLine(`(${this.inputData[0]} console) > `, data, "");
            return;
          case "sign":
            this.modeText = [`(${this.inputData[0]} console) > `];
            this.enterText = [`${this.signHelp[this.inputData.length - 1]}`];
            if (this.inputData.length == 5)
              this.classData[0] = [`text-color-red`];
            this.addLine(`(${this.inputData[0]} console) > `, data, "");
            return;
          case "find":
            this.modeText = [`(${this.inputData[0]} console) > `];
            this.enterText = [`Enter your Token`];
            this.addLine(`(${this.inputData[0]} console) > `, data, "");
            return;
          default:
            this.modeText = [`(base console) > `];
            this.enterText = [
              `Choose Menu 1.login 2.sign 3.find 4.clear 5.cd.. 6.cd home`,
            ];
            this.nothing(originalData);
            return;
        }
      }

      if (this.inputData.length > 0) {
        switch (data) {
          case "cd..":
          case "cd ..":
            this.goBack(originalData);
            return;
          case "cd home":
            this.addLine(`(${this.inputData[0]} console) > `, data, "");
            this.baseMode();
            return;
        }

        switch (this.inputData[0]) {
          case "login":
            this.loginMode(originalData);
            break;
          case "sign":
            this.signMode(originalData);
            break;
          case "find":
            this.findMode(data, originalData);
            break;
        }
      } else {
        switch (data) {
          case "login":
            this.loginMode(data, originalData);
            break;
          case "sign":
            this.signMode(data, originalData);
            break;
          case "find":
            this.findMode(data, originalData);
            break;
          default:
            this.nothing(originalData);
            break;
        }
      }
    },
    baseMode() {
      this.inputType = "text";
      this.rootText = `Kanboo bash(base console) > `;
      this.printToken = false;
      this.signReg = false;
      this.inputData.length = 0;
    },

    loginMode(data, originalData) {
      if (this.inputText == "") return;

      switch (this.inputData.length) {
        case 0:
          this.rootText = `Kanboo bash(login console) > `;
          this.inputData.push(data);
          this.addLine(`(base console) > `, originalData, "");
          this.addLine(`(login console) > `, `Enter your ID`, `com`);
          return;
        case 1:
          // 아이디 입력
          this.form(data, "ID");
          this.inputData.push(data);
          this.addLine(`(login console) > `, `Enter your PW`, `com`);
          this.inputType = "password";
          return;
        case 2:
          // 비밀번호 입력
          this.form(data, "PW");
          this.inputData.push(data);
          this.loginAccess();
          return;
      }
    },

    signMode(data, originalData) {
      if (this.signReg) return;
      switch (this.inputData.length) {
        case 0: // 아이디 입력 안내문
          this.rootText = `Kanboo bash(sign console) > `;
          this.inputData.push(data);
          this.addLine(`(base console) > `, originalData, "");
          this.addLine(`(sign console) > `, `${this.signHelp[0]}`, `com`);
          return;
        case 1:
          // 아이디 입력, 비밀번호 입력 안내문
          this.form(data, "ID");
          this.addLine(`(sign console) > `, `${this.signHelp[1]}`, `com`);
          this.inputData.push(data);
          this.inputType = "password";
          return;
        case 2:
          // 비번 입력 , 비밀번호 체크 안내문
          this.form(data, "PW");
          this.addLine(`(sign console) > `, `${this.signHelp[2]}`, `com`);
          this.inputData.push(data);
          this.inputType = "password";
          return;
        case 3:
          // 비번 확인 입력, 닉네임 입력 안내문
          this.form(data, "PWCheck");
          if (this.inputData[2] !== data) {
            this.addLine(`(sign console) > `, `${this.signHelp[2]}`, `com`);
            return;
          }
          this.addLine(`(sign console) > `, `${this.signHelp[3]}`, `com`);
          this.inputData.push(data);
          this.inputType = "text";
          return;
        case 4:
          // 닉네임 입력, 동의 확인
          this.form(data, "nickName");
          this.inputData.push(data);
          this.addLine(
            `(sign console) > `,
            `${this.signHelp[4]}`,
            `text-color-red`
          );
          this.inputType = "text";
          return;
        case 5:
          // 동의 확인 입력, 폰 입력 안내문
          this.form(data, "phoneChk");
          if ("Y" !== data && "y" !== data) {
            this.addLine(
              `(sign console) > `,
              `${this.signHelp[4]}`,
              `text-color-red`
            );
            return;
          }
          this.inputData.push(data);
          this.addLine(`(sign console) > `, `${this.signHelp[5]}`, `com`);
          return;
        case 6:
          // 저나번호 입력
          this.form(data, "phone");
          this.inputData.push(data);
          this.signAccess();
          break;
      }
    },
    findMode(data, originalData) {
      if (this.inputText == "") return;
      switch (this.inputData.length) {
        case 0:
          this.rootText = `Kanboo bash(find console) > `;
          this.addLine(`(base console) > `, originalData, "");
          this.inputData.push(originalData);
          this.addLine(`(find console) > `, `ID or PW ?`, `com`);
          return;
        case 1:
          this.addLine(`(find console) > `, data, "");
          this.inputData.push(data);
          this.addLine(`(find console) > `, `Enter your Token`, `com`);
          return;
        case 2:
          this.addLine(`(find console) > `, originalData, "");
          this.inputData.push(data);
          this.findUserInfo();
      }
      return;
    },
    form(data, position) {
      switch (position) {
        case "PW":
        case "PWCheck":
          this.addLine(
            `(${this.inputData[0]} console) > `,
            this.printPW(data),
            ""
          );
          break;
        case "phone":
          data = `+82) ${data}`;

          this.addLine(`(${this.inputData[0]} console) > `, data, "");
          break;
        default:
          this.addLine(`(${this.inputData[0]} console) > `, data, "");
          break;
      }
    },
    nothing(data) {
      // 키워드 아닐때 그냥 추가
      this.addLine(`(base console) > `, data, "");
    },
    goBack(data) {
      // 뒤로 가기
      this.addLine(`(${this.inputData[0]} console) > `, data, "");
      this.inputData.pop();
      if (this.inputData.length === 0) {
        return this.baseMode();
      } else {
        switch (this.inputData[0]) {
          case "login":
            switch (this.inputData.length) {
              case 1:
                this.addLine(
                  `(${this.inputData[0]} console) > `,
                  `Enter your ID`,
                  `com`
                );
                return;
              case 2:
                this.addLine(
                  `(${this.inputData[0]} console) > `,
                  `Enter your PW`,
                  `com`
                );
                return;
              case 3:
                this.addLine(
                  `(${this.inputData[0]} console) > `,
                  `Enter your nickName`,
                  `com`
                );
                return;
            }
            return;
          case "sign":
            switch (this.inputData.length) {
              case 1: // 아이디 안내문 출력
                this.addLine(`(sign console) > `, `${this.signHelp[0]}`, `com`);
                this.inputType = "password";
                return;
              case 2: // 비번 안내문
                this.addLine(`(sign console) > `, `${this.signHelp[1]}`, `com`);
                this.inputType = "password";
                return;
              case 3: // 비번 확인 안내문
                this.addLine(`(sign console) > `, `${this.signHelp[1]}`, `com`);
                this.inputType = "text";
                this.inputData.pop();
                return;
              case 4: // nick 안내문
                this.addLine(`(sign console) > `, `${this.signHelp[3]}`, `com`);
                return;
              case 5: // 동의 안내문
                this.addLine(`(sign console) > `, `${this.signHelp[4]}`, `com`);
                return;
            }
            return;
          case "find":
            switch (this.inputData.length) {
              case 1:
                this.addLine(`(find console) > `, `ID or PW ?`, `com`);
                return;
              case 2:
                this.addLine(`(find console) > `, `Enter your Token`, `com`);
                return;
            }
            return;
        }
      }
    },
    printPW(data) {
      // 비밀번호 ***로 표시
      var str = "";
      for (let i = 0; i < data.length; i++) {
        str += "•";
      }
      return str;
    },
    loginAccess() {
      this.addLine(`(login console) > `, `Loading...`, "com");
      let header = null;
      let loginInfo = {
        access: "login",
        data: {
          memId: this.inputData[1],
          memPass: this.inputData[2],
        },
      };
      header
      loginInfo

      // 자바 로그인 로직 작성 후 주석 해제
      // this.axios
      //   .post("http://localhost:8099/access/login", header, {
      //     params: {
      //       memId: this.inputData[1],
      //       memPass: this.inputData[2],
      //     },
      //   })
      //   .then((data) => {
      //     sessionStorage.setItem("token", data.data);
      //     if (data.data !== "fail") {
      //       this.addLine(`(login console) > `, `success`, `com`);
      //       this.login(loginInfo);
      //     } else {
      //       this.addLine(`(login console) > `, `Login access Fail`, `com`);
      //       this.addLine(`(base console) > `, `Choose Menu`, `com`);
      //     }
      //   })
      //   .catch(() => {
      //     this.addLine(`(login console) > `, `Login access Fail`, `com`);
      //     this.addLine(`(base console) > `, `Choose Menu`, `com`);
      //   });
      // this.baseMode();

      this.$router.push("/projects")

    },

    signAccess() {
      this.addLine(`(sign console) > `, `Loading`, "com");

      let signInfo = {
        access: this.inputData[0],
        data: {
          memId: this.inputData[1],
          memPass: this.inputData[2],
          memNick: this.inputData[4],
          memCelNum: this.inputData[6],
        },
      };


      let header = null;

      this.axios
        .post("http://localhost:8099/access/sign", header, {
          params: {
            memId: signInfo.data.memId,
            memPass: signInfo.data.memPass,
            memNick: signInfo.data.memNick,
            memCelNum: signInfo.data.memCelNum,
          },
        })
        .then((token) => {
          this.printToken = true;
          this.addLine(
            `(sign console) > `,
            `Your Token : ${token.data}`,
            `com token`
          );
          this.tokenText = token.data;
          this.sign(signInfo);
        })
        .catch((err) => {
          console.error(err);
          this.addLine(`(sign console) > `, `Sign access Fail`, `com`);
          this.addLine(`(base console) > `, `Choose Menu`, `com`);
        });
      this.baseMode();
    },

    findUserInfo() {
      let url, mode;
      let header = null;
      let params = { memToken: this.inputData[2] };

      switch (this.inputData[1]) {
        case "id":
          url = "findId";
          mode = "id";
          break;
        case "pw":
          url = "resetPw";
          mode = "pw";
          break;
      }

      this.axios
        .post(`http://localhost:8099/access/${url}`, header, { params })
        .then((data) => {
          switch (mode) {
            case "id":
              this.addLine(
                `(find console) > `,
                `Your ID : ${data.data} `,
                "com"
              );
              break;
            case "pw":
              if (data.data == true) {
                this.addLine(
                  `(find console) > `,
                  `A temporary password has been sent via SMS.`,
                  "com"
                );
              } else {
                this.addLine(
                  `(find console) > `,
                  `You entered an Invalid Token`,
                  "com"
                );
              }
              break;
          }
        })
        .catch(() => {
          this.addLine(
            `(find console) > `,
            `You entered an Invalid Token`,
            "com"
          );
        });
      this.baseMode();
    },

    addLine(mode, enter, classdata) {
      // 한줄 추가
      this.consoleText.push(`Kanboo bash`);
      this.modeText.push(mode);
      this.enterText.push(enter);
      this.classData.push(classdata);
      this.inputText = "";
    },
    copyToken() {
      if (this.printToken) {
          navigator.clipboard.writeText(this.tokenText)
        }
        this.printToken = false;
    },
    focus() {
      document.getElementById("inputBox").focus();
      document.getElementById("inputBox").scrollIntoView();
    },
    regex() {
      let stringReg = /^[a-z0-9]{6,20}$/;
      let phoneReg = /(?:\d{3}|\d{4})-\d{4}$/;
      let words = [
        "login",
        "sign",
        "find",
        "clear",
        "cd..",
        "cd ..",
        "cd home",
      ];

      if (this.inputData.length > 0 && this.inputData[0] === "sign") {
        switch (this.inputData.length) {
          case 5:
            stringReg = false;
            break;
          case 6:
            this.signReg = !phoneReg.test(this.inputText);
            break;
          default:
            this.signReg = !stringReg.test(this.inputText);
            break;
        }
      }

      let target = this.inputText.toLowerCase();

      for (let word of words) {
        if (word === target) {
          this.inputType = "text";
          this.reservedWord = true;
          break;
        } else {
          this.reservedWord = false;
          if (
            (this.inputData.length === 2 || this.inputData.length === 3) &&
            this.inputData[0] !== "find"
          ) {
            this.inputType = "password";
          }
        }
      }
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: "NeoDunggeunmo";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.3/NeoDunggeunmo.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

* {
  font-family: "NeoDunggeunmo";
  font-size: 22px;
}

.terminal-container {
  height: calc(100vh - 70px);
}

.console {
  color: #00ab26;
  height: 100%;
  padding: 20px;
}

input {
  width: 33%;
  outline: none;
  border: none;
  color: #fff;
  background: #16161a;
}

.user-text {
  color: #fff;
}

.console-text {
  color: #00ab26;
}

.com {
  color: #00ab26;
}

.text-color-red {
  color: red;
}
.text-color-orange {
  color: darkorange;
}

.icons {
  margin-top: 5px;
  width: 20px;
  vertical-align: sub;
  display: inline-block;
}

.copy {
  cursor: pointer;
}
</style>