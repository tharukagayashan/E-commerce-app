<template>

    <div class="container">
        <div class="row col-12">
            <h3 class="mt-1 mb-2">Our Categories</h3>
            <router-link :to="{name:'AddCategory'}">
                <button class="btn btn-light mb-3" style="float: right;">Add Category</button>
            </router-link>
        </div>
        <div class="row">

            <div class="row">
                <div v-for="category of categories" :key="category.categoryId" class="col-xl-3 col-md-6 d-flex mb-3">
                    <CategoryBox :category="category"></CategoryBox>
                </div>
            </div>

        </div>
    </div>

</template>

<script>

import CategoryBox from '../../components/Category/CategoryBox.vue';
import axios from 'axios'
export default {
    name: "CategoryView",
    components: { CategoryBox },
    data() {
        return {
            url: "http://localhost:8000/category",
            categories: [],
        };
    },
    methods: {
        async getCategories() {
            await axios.get(this.url)
                .then((response) => {
                    this.categories = response.data.data;
                }).catch((err) => {
                    console.log(err.response.data);
                });
        },
    },
    mounted() {
        this.getCategories();
    }
}
</script>

<style scoped>

</style>