<template>
  <v-app>
    <v-toolbar app>
      <v-toolbar-title class="headline text-uppercase">
        <span>SEGroup</span>
        <span class="font-weight-light">Web Application</span>
      </v-toolbar-title>
    </v-toolbar>

    <v-content>
      <router-view/>
    </v-content>
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { SEGroupBuilder } from "@/services/builderService";
  
@Component({
  name: 'App',
  provide: {
    SEGroupBuilder: SEGroupBuilder
  }
})
export default class App extends Vue {
}
</script>
