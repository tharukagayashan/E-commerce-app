<script>
import axios from 'axios';
export default {
    data() {
        return {
            categoryName: "",
            description: "",
            imageUrl: "",
        }
    },
    methods: {
        addCategory() {
            const newCategory = {
                categoryName: this.categoryName,
                description: this.description,
                imageUrl: this.imageUrl,
            };
            const BASEURL = "http://localhost:8000";

            axios.post(BASEURL + "/category", newCategory)
                .then((response) => {
                    console.log(response);
                    alert(response.data.message)
                })
                .catch((err) => {
                    alert(err.response.data);
                    console.log(err.response.data);
                })
        }
    }
}
</script>

<template>
    <div class="container">
        <div class="row">
            <div class="col-12 text-center">
                <h3 class="pt-3">Add Category</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-3"></div>
            <div class="col-6">
                <form>
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" v-model="categoryName" />
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <textarea type="text" class="form-control" v-model="description"></textarea>
                    </div>
                    <div class="form-group">
                        <label>Image</label>
                        <input type="text" class="form-control" v-model="imageUrl" />
                    </div>
                    <input type="button" class="btn btn-primary" value="Submit" @click="addCategory" />
                </form>
            </div>
            <div class="col-3"></div>
        </div>
    </div>
</template>

<style scoped>

</style>