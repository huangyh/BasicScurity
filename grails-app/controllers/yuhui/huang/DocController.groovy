package yuhui.huang

import org.springframework.dao.DataIntegrityViolationException

class DocController {
	
	def beforeInterceptor = [action:this.&auth]
	
	def auth() {
		if(!session.user) {
			redirect(controller:"User", action:"login")
			return false
		}
		println session.user?.userName
	}
	
	
	def save = {
		def docInstance = new Doc(params)
	
		//handle uploaded file
		def uploadedFile = request.getFile('filedata')
		if(!uploadedFile.empty){
			println "Class: ${uploadedFile.class}"
			println "Name: ${uploadedFile.name}"
			println "OriginalFileName: ${uploadedFile.originalFilename}"
			println "Size: ${uploadedFile.size}"
			println "ContentType: ${uploadedFile.contentType}"

			def webRootDir = servletContext.getRealPath("/")
			def userDir = new File(webRootDir, "/upload")
			userDir.mkdirs()
			uploadedFile.transferTo( new File( userDir, uploadedFile.originalFilename))
			docInstance.fileName = uploadedFile.originalFilename
		}

		if(!docInstance.hasErrors() && docInstance.save()) {
			flash.message = "Doc ${docInstance.id} created"
			redirect(action:"show",id:docInstance.id)
		}
		else {
			render(view:'create',model:[entryInstance:docInstance])
		}
	}

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [docInstanceList: Doc.list(params), docInstanceTotal: Doc.count()]
    }

    def create() {
        [docInstance: new Doc(params)]
    }


    def show(Long id) {
        def docInstance = Doc.get(id)
        if (!docInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'doc.label', default: 'Doc'), id])
            redirect(action: "list")
            return
        }

        [docInstance: docInstance]
    }

    def edit(Long id) {
        def docInstance = Doc.get(id)
        if (!docInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'doc.label', default: 'Doc'), id])
            redirect(action: "list")
            return
        }

        [docInstance: docInstance]
    }

    def update(Long id, Long version) {
        def docInstance = Doc.get(id)
        if (!docInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'doc.label', default: 'Doc'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (docInstance.version > version) {
                docInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'doc.label', default: 'Doc')] as Object[],
                          "Another user has updated this Doc while you were editing")
                render(view: "edit", model: [docInstance: docInstance])
                return
            }
        }

        docInstance.properties = params

        if (!docInstance.save(flush: true)) {
            render(view: "edit", model: [docInstance: docInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'doc.label', default: 'Doc'), docInstance.id])
        redirect(action: "show", id: docInstance.id)
    }

    def delete(Long id) {
        def docInstance = Doc.get(id)
        if (!docInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'doc.label', default: 'Doc'), id])
            redirect(action: "list")
            return
        }

        try {
            docInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'doc.label', default: 'Doc'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'doc.label', default: 'Doc'), id])
            redirect(action: "show", id: id)
        }
    }
}
