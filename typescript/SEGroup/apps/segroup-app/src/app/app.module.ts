import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { SuiModule } from 'ng2-semantic-ui';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { SegroupService } from './segroup.service';
import { MainMenuComponent } from './main-menu/main-menu.component';

@NgModule({
  declarations: [AppComponent, MainMenuComponent],
  imports: [
    BrowserModule,
    FormsModule, 
    SuiModule,
    HttpClientModule, 
    AppRoutingModule,         
  ],
  providers: [SegroupService],
  bootstrap: [AppComponent]
})
export class AppModule {}
