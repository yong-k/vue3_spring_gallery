import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import router from '@/scripts/router'
import App from './App.vue'

const app = createApp(App)
app.use(router)
app.use(createPinia())
//app.use(useAccountStore) => 안해줘도 된다.
app.mount('#app')

// 한줄로도 가능
//createApp(App).use(createPinia()).use(router).mount('#app')
