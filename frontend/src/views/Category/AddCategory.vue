<script>
import axios from 'axios'

export default {
    data() {
        return{
        categoryName: "",
        description: "",
        imageUrl: "",
        };
    },
    methods: {
        addCategory() {

            const BASEURL = "http://localhost:8000";

            const newCategory = {
                categoryName: this.categoryName,
                description: this.description,
                imageUrl: this.imageUrl
            };

            console.log(newCategory);
            axios.post(BASEURL + "/category",newCategory)
            .then((response)=>{
                console.log(response.data.data);
                alert(response.data.message);
            })
            .catch((err)=>{
                console.log(err.response.data);
                alert(err.response.data)
            })

        }
    }
}
</script>


<template>

    <div class="container">
        <div class="row col-12">
            <h1 class="pb-2 b-3">ADD CATEGORY</h1>
        </div>
        <div class="row">
            <div class="col-3">
                
            </div>
            <div class="col-6">

                <form>
                    <div class="form-group mb-3">
                        <label for="Name" class="form-label">Name</label>
                        <input type="text" class="form-control" v-model="categoryName">
                    </div>
                    <div class="form-group mb-3">
                        <label for="Name" class="form-label">Description</label>
                        <textarea type="text" class="form-control" v-model="description"></textarea>
                    </div>
                    <div class="form-group mb-3">
                        <label for="Name" class="form-label">Image URL</label>
                        <input type="text" class="form-control" v-model="imageUrl">
                    </div>
                    <input type="button" value="Submit" class="btn btn-primary" @click="addCategory">
                </form>

            </div>
            <div class="col-3"></div>
        </div>
    </div>

</template>


<style scoped>

</style>