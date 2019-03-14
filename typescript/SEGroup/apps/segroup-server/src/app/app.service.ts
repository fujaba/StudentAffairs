import { Injectable } from '@nestjs/common';

@Injectable()
export class AppService {
  getData(): string {
    return `{ "yaml": "- alice: Student's \\n name: Alice"}`;
  }
}
