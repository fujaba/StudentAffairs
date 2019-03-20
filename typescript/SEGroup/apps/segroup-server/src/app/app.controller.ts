import { Controller, Get, Post, Param, Res, Body, Query } from '@nestjs/common';

import { AppService } from './app.service';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get('/get')
  getSharedEvents(@Query('since') since: string, @Query('caller') caller: string, @Res() response: Response): Promise<void> {
    return this.appService.getSharedEvents(since, caller, response);
  }

  @Post('/put')
  putSharedEvents(@Query('caller') caller: string, @Body() body: any, @Res() res: Response): Promise<void> {
    return this.appService.putSharedEvents(caller, body, res);
  }
}
