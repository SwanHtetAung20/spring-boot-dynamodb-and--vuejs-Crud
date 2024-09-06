import "./assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";
import { createVfm } from "vue-final-modal";
import App from "./App.vue";
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";
import "vue-final-modal/style.css";
import "primeicons/primeicons.css";
import router from "./router";

const app = createApp(App);

app.use(createPinia());
app.use(Toast);
app.use(createVfm());
app.use(router);

app.mount("#app");
