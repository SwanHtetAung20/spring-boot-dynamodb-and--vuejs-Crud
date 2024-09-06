<script setup lang="ts">
import { ref } from "vue";
import { useCounterStore } from "../stores/counter";
import { useToast } from "vue-toastification";
import router from "@/router";
import { RouterLink } from "vue-router";

const main = useCounterStore();
const email = ref("");
const password = ref("");
const date = ref("");
const toast = useToast();

const { signUp } = main;

const signUpHandler = async () => {
  const myObj = {
    email: email.value,
    password: password.value,
    date: date.value,
  };
  (email.value = ""), (password.value = ""), (date.value = "");
  try {
    const res = await signUp(myObj);
    if (!res.user) {
      toast.error(res.message);
      return;
    }
    toast.success(res.message);
    router.push("/");
  } catch (error: any) {
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
        <h1 class="header">SignUp Here</h1>
        <form @submit.prevent="signUpHandler">
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

          <!-- this is not good it should be default -->
          <div>
            <input
              type="text"
              placeholder="enter date..."
              class="inputType"
              v-model="date"
            />
          </div>

          <div class="btn">
            <button class="signUpBtn" type="submit">Sign up</button>
          </div>
        </form>
        <div class="footer">
          <span
            >Already have an account? back to
            <RouterLink to="/"
              ><strong :style="{ color: 'green', cursor: 'pointer' }"
                >Login</strong
              ></RouterLink
            >
            Please!</span
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

.signUpBtn {
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
