import { NgModule } from '@angular/core';
import { AnexoListComponent } from './anexo-list/anexo-list.component';
import { Routes, RouterModule } from '@angular/router';
const routes: Routes = [
    { path: '', component: AnexoListComponent },
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
export class AnexoRoutingModule {}
