<template>
  <div>
    <canvas ref="canvas"></canvas>
    <button @click="previewPDF">预览</button>
    <button @click="downloadPDF">下载</button>
  </div>
</template>

<script>
import pdfjsLib from 'pdfjs-dist'

export default {
  name: 'pdfViewer',
  data() {
    return {
      pdfDoc: null,
      pageNum: 1,
      pageRendering: false,
      pageNumPending: null,
      canvas: null,
      ctx: null,
      scale: 1.5
    }
  },
  mounted() {
    this.canvas = this.$refs.canvas
    this.ctx = this.canvas.getContext('2d')
  },
  methods: {
    renderPage(num) {
      this.pageRendering = true
      this.pdfDoc.getPage(num).then(page => {
        const viewport = page.getViewport({scale: this.scale})
        this.canvas.height = viewport.height
        this.canvas.width = viewport.width

        const renderContext = {
          canvasContext: this.ctx,
          viewport: viewport
        }

        const renderTask = page.render(renderContext)

        renderTask.promise.then(() => {
          this.pageRendering = false
          if (this.pageNumPending !== null) {
            this.renderPage(this.pageNumPending)
            this.pageNumPending = null
          }
        })
      })

      document.getElementById('page-num').textContent = num
    },
    queueRenderPage(num) {
      if (this.pageRendering) {
        this.pageNumPending = num
      } else {
        this.renderPage(num)
      }
    },
    onPrevPage() {
      if (this.pageNum <= 1) {
        return
      }
      this.pageNum--
      this.queueRenderPage(this.pageNum)
    },
    onNextPage() {
      if (this.pageNum >= this.pdfDoc.numPages) {
        return
      }
      this.pageNum++
      this.queueRenderPage(this.pageNum)
    },
    previewPDF () {
      const url = '../../../assets/CandidateNotice.pdf'
      pdfjsLib.getDocument(url).promise.then(pdfDoc_ => {
        this.pdfDoc = pdfDoc_
        document.getElementById('page-count').textContent = this.pdfDoc.numPages
        this.renderPage(this.pageNum)
      })
    },
    downloadPDF () {
      const url = '../../../assets/CandidateNotice.pdf'
      pdfjsLib.getDocument(url).promise.then(pdfDoc_ => {
        const fileName = 'CandidateNotice.pdf'
        const fileData = pdfDoc_.output()
        const blob = new Blob([fileData], {type: 'application/pdf'})
        const link = document.createElement('a')
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        link.click()
      })
    }
  }
}
</script>
