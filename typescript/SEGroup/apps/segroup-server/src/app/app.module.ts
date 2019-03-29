import { Module } from '@nestjs/common';
// import { MongooseModule } from '@nestjs/mongoose';

import { AppController } from './app.controller';
import { AppService } from './app.service';
import { EventSchema } from './event.schema';

@Module({
  imports: [
    // MongooseModule.forRoot('mongodb://localhost:27017/Event'),
    // MongooseModule.forFeature([{ name: 'Event', schema: EventSchema }])
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule { }
