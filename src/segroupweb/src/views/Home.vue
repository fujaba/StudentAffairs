<template>
  <div class="home">
    <v-container>
      <v-layout align-center justify-center row>
        <v-flex xs4>
          <v-text-field
            label="Head"
            single-line
            v-model="profName">
          </v-text-field>
        </v-flex>
        <v-flex xs1>
          <v-btn v-on:click="onCreateGroup">Create Group</v-btn>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import { SEGroup } from "@/model";
import Organize from "@/components/Organize.vue";
import { SEGroupBuilder } from "@/services/builderService";

@Component({
  components: {
    organize: Organize
  }
})
export default class Home extends Vue {
  groupSEGroup: SEGroup = {};
  profName: string = '';
  
  @Inject('SEGroupBuilder')
  private gb!: SEGroupBuilder;
  
  onCreateGroup(event: Event) {
    if (this.profName) {
      
    }
  }
}
</script>
