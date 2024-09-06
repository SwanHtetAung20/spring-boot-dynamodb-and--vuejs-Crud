<script setup lang="ts">
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useCounterStore } from "@/stores/counter";
import { useToast } from "vue-toastification";
import UpdateForm from "./UpdateForm.vue";
import { Interface } from "readline";

const main = useCounterStore();
const { userList, isTrue, selectedUser } = storeToRefs(main);
const { findAll, deleteUser, modifyProfilePhoto } = main;
const toast = useToast();

onMounted(async () => {
  try {
    await findAll();
    console.log("UserList", userList);
  } catch (error: any) {
    if (error.response) {
      toast.error(
        error.response.data.message ||
          "An unexpected error occurred. Please try again later."
      );
    } else {
      toast.error("An unexpected error occurred. Please try again later.");
    }
  }
});

const deleteUserHandler = async (id: string, date: string) => {
  try {
    const confirm = window.confirm("Are you sure? you want to delete!");
    if (confirm) {
      const res = await deleteUser(id, date);
      if (!res.message) {
        toast.error(res.message);
        return;
      }
      toast.success(res.message);
    }
  } catch (error: any) {
    if (error.response) {
      toast.error(error.response.data.message);
    } else {
      toast.error("An unexpected error occurred. Please try again later.");
    }
  }
};

const isShow = (user: Interface) => {
  selectedUser.value = user;
  isTrue.value = !isTrue.value;
};

const changeFile = (id: string, date: string) => {
  const fileInput = document.getElementById(
    `fileInput-${id}/${date}`
  ) as HTMLInputElement;
  if (fileInput) {
    fileInput.click();
  }
};

const fileHandler = async (event: Event, id: string, date: string) => {
  try {
    const fileInput = event.target as HTMLInputElement;
    const file = fileInput.files ? fileInput.files[0] : null;

    if (!file) {
      toast.error("photo cannot be empty!");
      return;
    }
    const res = await modifyProfilePhoto(id, date, file);
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
  <div class="header">
    <div class="sub-header">
      <table class="userTable">
        <thead>
          <tr class="tbattr">
            <th>No</th>
            <th>Name</th>
            <th>Email</th>
            <th>Created Date</th>
            <th>Phone Number</th>
            <th>Photo</th>
            <th>Update</th>
            <th>Delete</th>
            <th>Change</th>
          </tr>
        </thead>
        <tbody>
          <tr
            class="tbattr boddy"
            v-for="(user, index) in userList"
            :key="index"
          >
            <td>{{ index + 1 }}</td>
            <td>{{ user.name }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.created_date }}</td>
            <td>{{ user.phoneNumber }}</td>
            <td>
              <div v-if="user.photo">
                <img
                  :src="'data:image/**;base64,' + user.photo"
                  alt="Profile Image"
                  :style="{ height: '100px', width: '100px' }"
                />
              </div>
              <div v-else>UNDEFINED</div>
            </td>
            <td>
              <button class="actionBtn" @click="isShow(user)">
                <i class="pi pi-user-edit"></i> Update
              </button>
            </td>
            <td>
              <button
                class="actionBtn"
                @click="deleteUserHandler(user.id, user.created_date)"
              >
                <i class="pi pi-trash"></i>
                Delete
              </button>
            </td>
            <td>
              <button
                class="actionBtn"
                @click="changeFile(user.id, user.created_date)"
              >
                <i class="pi pi-images"></i> Change Profile
              </button>
            </td>
            <input
              type="file"
              :id="`fileInput-${user.id}/${user.created_date}`"
              style="display: none"
              @change="fileHandler($event, user.id, user.created_date)"
            />
          </tr>
        </tbody>
      </table>
    </div>
    <div v-show="isTrue" class="updateForm">
      <div class="sub-updateForm">
        <UpdateForm />
      </div>
    </div>
  </div>
</template>

<style scoped>
.header {
  max-width: 1024px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.sub-header {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.userTable {
  position: sticky;
  top: 0;
  width: 100%;
}

.tbattr {
  text-align: center;
}

.boddy {
  padding: 4px;
}

.actionBtn {
  padding: 15px;
  border-radius: 5px;
  border: 1px solid lightgray;
  cursor: pointer;
}
.updateForm {
  width: 80%;
}

.sub-updateForm {
  text-align: center;
}
</style>
