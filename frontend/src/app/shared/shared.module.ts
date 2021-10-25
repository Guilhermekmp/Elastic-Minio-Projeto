import { ServiceModule } from './services/service.module';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { PRIMENG_IMPORTS } from './primeng-imports';

@NgModule({
    imports: [
        PRIMENG_IMPORTS,
        CommonModule,
        ServiceModule
    ],
    exports: [
        PRIMENG_IMPORTS,
        CommonModule,
        ServiceModule
    ],
})
export class SharedModule { }
