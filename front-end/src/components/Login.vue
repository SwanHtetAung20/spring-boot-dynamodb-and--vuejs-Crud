<script setup lang="ts">
import { ref } from "vue";
import { useCounterStore } from "../stores/counter";
import { useToast } from "vue-toastification";
import { useRouter } from "vue-router";
import { RouterLink } from "vue-router";

const main = useCounterStore();
const email = ref("");
const password = ref("");
const toast = useToast();
const router = useRouter();

const { login } = main;

const loginHandler = async () => {
  const myObj = {
    email: email.value,
    password: password.value,
  };
  try {
    const res = await login(myObj);
    if (!res.user) {
      toast.error(res.message);
      return;
    }
    toast.success(res.message);
    router.push("/home");
  } catch (error: any) {
    (email.value = ""), (password.value = "");
    if (error.response) {
      toast.error(error.response.data.message);
    } else {
      toast.error("An unexpected error occurred. Please try again later.");
    }
  }
};
</script>

<template>
  <div class="container">
    <div class="sub-container">
      <div class="child-container">
        <h1 class="header">Login Here</h1>
        <form @submit.prevent="loginHandler">
          <div>
            <input
              type="email"
              placeholder="enter email..."
              class="inputType"
              v-model="email"
            />
          </div>
          <div>
            <input
              type="password"
              placeholder="enter password..."
              class="inputType"
              v-model="password"
            />
          </div>

          <div class="btn">
            <button class="loginBtn" type="submit">Login</button>
          </div>
        </form>
        <div class="footer">
          <span
            >If you don't have an account!Please
            <RouterLink to="/sign-up"
              ><strong :style="{ color: 'green', cursor: 'pointer' }"
                >Sign Up</strong
              ></RouterLink
            >here</span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.html,
body {
  margin: 0;
  padding: 0;
}
.container {
  max-width: 1024px;
  margin: 0 auto;
}

.sub-container {
  display: flex;
  justify-content: center;
  width: 100%;
}

/* .child-container {
  width: 100%;
} */

.header {
  text-align: center;
}

.loginBtn {
  padding: 15px;
  border-radius: 5px;
  border: 1px solid lightgray;
  cursor: pointer;
}

.inputType {
  width: 300px;
  padding: 15px;
  margin-bottom: 10px;
  border-radius: 10px;
}
.btn {
  text-align: end;
  width: 100%;
}

.footer {
  margin-top: 10px;
}

@media (max-width: 333px) {
  .child-container {
    width: 80%;
  }

  .btn {
    width: 90%;
  }

  .loginBtn {
    padding: 10px;
  }

  .inputType {
    width: 200px;
  }
}
</style>
