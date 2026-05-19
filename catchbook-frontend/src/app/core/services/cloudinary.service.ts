import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CloudinaryService {

  private cloudName = 'dnuq2kfrd';
  private uploadPreset = 'catchbook_upload';

  constructor(private http: HttpClient) {}

  async uploadImage(file: File): Promise<string | null> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', this.uploadPreset);

    try {
      const response: any = await this.http
        .post(`https://api.cloudinary.com/v1_1/${this.cloudName}/image/upload`, formData)
        .toPromise();
      return response.secure_url;
    } catch (err) {
      console.error('Erreur upload Cloudinary', err);
      return null;
    }
  }
}
