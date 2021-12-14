<template>
  <div class="editor-container">
    <Codemirror v-model:value="html"
                :options="cmOptions"
                class="code-container"/>
    <v-md-preview-html :v-model="html" 
                        preview-class="vuepress-markdown-body" 
                        :html="html"></v-md-preview-html>
  </div>
</template>

<script>
import Codemirror from "codemirror-editor-vue3";
import "codemirror-editor-vue3/dist/style.css"
import "codemirror/theme/dracula.css"
import "codemirror/mode/htmlmixed/htmlmixed.js";

export default {
  components: { Codemirror },
  data() {
    return {
      html: '<h1 style="color: #000;">hello</h1>',
      cmOptions: {
        mode: 'text/html',
        theme: 'dracula',
        lineNumbers: true,
        smartIndent: true,
        indentUnit: 2,
        foldGutter: true,
        styleActiveLine: true,
      }
    }
  }
}
</script>

<style scoped>
.editor-container {
  width: 100%;
  display: flex;
}

.editor-container > * {
  width: 50%;
}

textarea {
  color: #000;
  resize: none;
}

.code-container {
  border: 1px solid #999;
  font-size: 20px;
}
</style>