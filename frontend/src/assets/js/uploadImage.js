export default {
  methods: {
    previewImage (data) {
      let reader = new FileReader()
      reader.onload = (function (file) {
        return function (ev) {
          console.log('ev: ')
          console.dir(ev)

          let imgForm = document.createElement('img')
          imgForm.setAttribute('id', 'selectedImage')
          imgForm.setAttribute('src', ev.target.result)
          imgForm.setAttribute('class', 'w-full h-auto')

          let box = document.getElementById('imgaeBox')
          box.appendChild(imgForm)
        }
      })(data)
      // console.log('dataURL: ' + reader.readAsDataURL(data));
      reader.readAsDataURL(data)
    },
    dragOverHandler (event) {
      event.preventDefault()
    },
    dropHandler (ev) {
      ev.preventDefault()
      console.log('File(s) dropped')

      document.getElementById('imageBox').innerHTML = ''

      if (ev.dataTransfer.items) {
        if (ev.dataTransfer.items[0].kind === 'file') {
          let file = ev.dataTransfer.items[0].getAsFile()
          this.previewImage(file)
        }
      } else {
        for (let i = 0; i < ev.dataTransfer.files.length; i++) {
          console.log(ev.dataTransfer.files[i])
        }
      }
    }
  }
}
