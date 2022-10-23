import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  private adminApiUrl = "http://localhost:8888/admin/files";

  constructor(private http: HttpClient) { }

  getAllImages(){
    return this.http.get(this.adminApiUrl);
  }

  upload(file: File) {
    const formData: FormData = new FormData();
    formData.append('files', file);
    const req = new HttpRequest('POST', this.adminApiUrl, formData, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.http.request(req);
  }

  deleteAllImages(ids: number[]) {
    return this.http.delete(this.adminApiUrl, {body: ids});
  }

}
