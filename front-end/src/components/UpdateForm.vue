<script setup lang="ts">
import { useCounterStore } from "@/stores/counter";
import { storeToRefs } from "pinia";
import { ref, computed } from "vue";
import { useToast } from "vue-toastification";

const main = useCounterStore();
const { selectedUser } = storeToRefs(main);

const { updateUser } = main;
const toast = useToast();

const user = computed<any>(
  () =>
    selectedUser.value || {
      id: "",
      created_date: "",
      email: "",
      password: "",
      phoneNumber: "",
    }
);

const updateFormHandler = async () => {
  try {
    const res = await updateUser(user.value);
    if (!res.user) {
      toast.error(res.message);
      return;
    }
    toast.success(res.message);
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
  <h1>Update Form</h1>
  <form @submit.prevent="updateFormHandler">
    <div>
      <input type="text" readonly class="inputType" v-model="user.id" />
    </div>
    <div>
      <input
        type="text"
        readonly
        class="inputType"
        v-model="user.created_date"
      />
    </div>
    <div>
      <input type="text" class="inputType" v-model="user.name" />
    </div>
    <div>
      <input
        type="email"
        placeholder="enter email..."
        class="inputType"
        v-model="user.email"
      />
    </div>

    <div>
      <input
        type="password"
        placeholder="enter password..."
        class="inputType"
        v-model="user.password"
      />
    </div>

    <div>
      <input
        type="text"
        placeholder="enter phone number..."
        class="inputType"
        v-model="user.phoneNumber"
      />
    </div>

    <div class="btn">
      <button class="updateBtn" type="submit">Update</button>
    </div>
  </form>
</template>

<style scoped>
.inputType {
  width: 300px;
  padding: 15px;
  margin-bottom: 10px;
  border-radius: 10px;
}

.updateBtn {
  padding: 15px;
  border-radius: 5px;
  border: 1px solid lightgray;
  cursor: pointer;
}
</style>
