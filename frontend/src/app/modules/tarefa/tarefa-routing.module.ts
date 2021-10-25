import { NgModule } from '@angular/core';
import { TarefaListComponent } from './tarefa-list/tarefa-list.component';
import { Routes, RouterModule } from '@angular/router';
const routes: Routes = [
    { path: '', component: TarefaListComponent },
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
export class TarefaRoutingModule {}
