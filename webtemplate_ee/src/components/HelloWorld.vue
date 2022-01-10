<template>
  <div class="hello">
    <h1>ESA Course Web Application Template</h1>
    <ratings-table/>
    <students-table/>
    <exams-table/>
  </div>
</template>

<script>
import RatingsTable from "./RatingsTable.vue";
import StudentsTable from './StudentsTable.vue'
import ExamsTable from "./ExamsTable.vue";
export default {
  components: { RatingsTable, StudentsTable, ExamsTable},
  name: 'HelloWorld',
  data() {
    return {

    }
  }
}
</script>

<style scoped>

</style>
