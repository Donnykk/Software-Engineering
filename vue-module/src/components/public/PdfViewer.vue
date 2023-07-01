<template>
  <div>
    <canvas ref="pdfCanvas"></canvas>
  </div>
</template>

<script>
import pdfjsLib from 'pdfjs-dist'

export default {
  name: "PdfViewer",
  mounted() {
    this.loadPDF()
  },
  methods: {
    async loadPDF() {
      const canvas = this.$refs.pdfCanvas
      const pdfURL = '../../assets/static/1.pdf' // 替换为实际的PDF文件路径

      const pdf = await pdfjsLib.getDocument(pdfURL).promise
      const page = await pdf.getPage(1) // 加载第一页，可以根据需要加载其他页

      const viewport = page.getViewport({ scale: 1 })
      const context = canvas.getContext('2d')
      canvas.height = viewport.height
      canvas.width = viewport.width

      const renderContext = {
        canvasContext: context,
        viewport: viewport
      }

      await page.render(renderContext).promise
    }
  }
}
</script>

<style scoped>
canvas {
  border: 1px solid #000;
}
</style>
