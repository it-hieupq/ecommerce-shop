import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Image} from "../../model/Image";
import {MediaService} from "../../services/media.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-media',
  templateUrl: './media.component.html',
  styleUrls: ['./media.component.css']
})
export class MediaComponent implements OnInit {

  images: Image[] = [];

  checkedImages: number[] = [];

  @ViewChild('filesInput')
  filesInput: ElementRef<HTMLInputElement> = {} as ElementRef;

  selectedFiles?: FileList;
  progressInfos: any[] = [];
  message: string[] = [];
  previews: any[] = [];

  constructor(private mediaService: MediaService) { }

  ngOnInit(): void {
    this.getAllImages();
  }

  getAllImages(){
    this.mediaService.getAllImages().subscribe((res: any) => {
      console.log(res)
      if(res && res.data && res.data.images){
        this.images = res.data.images;
      }
    });
  }

  selectFiles(event: any): void {
    this.message = [];
    this.progressInfos = [];
    this.selectedFiles = event.target.files;
    this.previews = [];
    if (this.selectedFiles && this.selectedFiles[0]) {
      const numberOfFiles = this.selectedFiles.length;
      for (let i = 0; i < numberOfFiles; i++) {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.compressImage(e.target.result, 180, 180).then( compressed => {
            this.previews.push(compressed);
          })
        };
        reader.readAsDataURL(this.selectedFiles[i]);
      }
    }
  }

  compressImage(src: string, newX: number, newY: number) {
    return new Promise((res, rej) => {
      const img = new Image();
      img.src = src;
      img.onload = () => {
        const elem = document.createElement('canvas');
        elem.width = newX;
        elem.height = newY;
        const ctx = elem.getContext('2d');
        ctx?.drawImage(img, 0, 0, newX, newY);
        const data = ctx?.canvas.toDataURL();
        res(data);
      }
      img.onerror = error => rej(error);
    })
  }

  uploadFiles(): void {
    this.message = [];
    if (this.selectedFiles) {
      for (let i = 0; i < this.selectedFiles.length; i++) {
        this.upload(i, this.selectedFiles[i]);
      }
    }
  }

  upload(idx: number, file: File): void {
    this.progressInfos[idx] = { value: 0, fileName: file.name };
    if (file) {
      this.mediaService.upload(file).subscribe({
        next: (event: any) => {

          if(event.body && event.body.data && event.body.data.files && event.body.data.files[0]){
            let tmp: Image = event.body.data.files[0];
            this.images.push(tmp);
          }

          if (event.type === HttpEventType.UploadProgress) {
            this.progressInfos[idx].value = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            this.message.push('Uploaded file: ' + file.name);
          }
        }, error: (err: any) => {
          this.progressInfos[idx].value = 0;
          this.message.push('Could not upload the file: ' + file.name);
        }});
    }
  }

  delete(){
    this.mediaService.deleteAllImages(this.checkedImages).subscribe((res: any) => {
      if(res && res.data && res.data.images){
        console.log(res)
        this.images = this.images.filter(e => !this.checkedImages.includes(e.imageId));
        this.checkedImages = [];
      }
    });
  }

  clearFiles(event: any) {
    event.target.files = undefined;
    this.selectedFiles = event.target.files;
    this.previews = [];
    this.message = [];
    this.progressInfos = [];
    this.filesInput.nativeElement.value = "";
  }

  onCheckboxChange(imageId: number, event: any) {
    if(event.target.checked) {
      this.checkedImages.push(imageId);
    } else {
      let index = this.checkedImages.indexOf(imageId);
      this.checkedImages.splice(index,1);
    }
  }

}
