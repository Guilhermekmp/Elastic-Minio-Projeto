import { NgModule } from '@angular/core';
import { ResponsavelListComponent } from './responsavel-list/responsavel-list.component';
import { Routes, RouterModule } from '@angular/router';
const routes: Routes = [
    { path: '', component: ResponsavelListComponent },
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
export class ResponsavelRoutingModule {}
